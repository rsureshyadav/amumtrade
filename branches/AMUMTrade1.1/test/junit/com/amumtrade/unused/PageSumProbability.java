package com.amumtrade.unused;

import java.util.*;


public class PageSumProbability {

	
	//>>"TRX",2.62,3.50,"2.43 - 2.67",2.43,2.67,2.14,5.34,-0.1741,-0.7187,"7/5/2013",+0.02,amum proj,No Opinion,No Opinion,No Opinion,No Opinion
	///>>"EVK",2.60,4.50,"2.50 - 2.61",2.50,2.61,0.99,3.55,-0.2971,+0.3671,"7/5/2013",0.00,amum proj,No Opinion,No Opinion,Buy,No Opinion
	//>>"AKG",2.20,2.63,"2.1001 - 2.20",2.1001,2.20,1.90,4.39,-0.1779,-0.8358,"7/5/2013",+0.03,amum proj,Hold,No Opinion,No Opinion,Buy
	//>>"AMS",2.20,4.50,"2.20 - 2.20",2.20,2.20,1.52,3.33,+0.0762,+0.1161,"7/5/2013",+0.0499,amum proj,No Opinion,No Opinion,Sell,No Opinion
	//>>"NSU",2.91,6.06,"2.84 - 2.94",2.84,2.94,2.65,4.98,-0.3906,-0.8233,"7/5/2013",-0.08,amum proj,Hold,Buy,Hold,Buy
	//>>"RTK",2.17,2.18,"2.095 - 2.18",2.095,2.18,1.82,3.18,+0.0091,-0.289,"7/5/2013",+0.07,amum proj,Buy,Buy,Hold,Strong Buy
	//>>"DGSE",2.70,3.86,"2.645 - 2.73",2.645,2.73,2.65,7.43,-0.7438,-2.3003,"7/5/2013",-0.05,amum proj,No Opinion,No Opinion,No Opinion,No Opinion

    private Map<Integer, Integer> pageNumberSum;
    private Map<Integer, List<Integer>> sumPageNumbers;

    public static void main(String[] args) {
            int maxPageNumber = 100;            
            int randomSum = 80;
            PageSumProbability psp = new PageSumProbability(maxPageNumber);
            System.out.println(psp.getPageNumberSum());
            System.out.println(psp.getSumPageNumbers());
            System.out.printf("random sum: %d probability of opening page # that equals random sum: %5.3f%%\n",
                    randomSum, 100*psp.getProbabilityOfSum(randomSum));
       
    }

    public PageSumProbability(int maxPageNumber) {
        this.pageNumberSum = new TreeMap<Integer, Integer>();
        this.sumPageNumbers = new TreeMap<Integer, List<Integer>>();

        for (int i = 1; i <= maxPageNumber; ++i) {
            int sum = this.calculateSumOfDigits(i);
            this.pageNumberSum.put(i, sum);
            List<Integer> pages = this.sumPageNumbers.get(sum);
            if (pages == null) {
                pages = new LinkedList<Integer>();
            }
            pages.add(i);
            this.sumPageNumbers.put(sum, pages);
        }
    }

    public static int calculateSumOfDigits(int pageNumber) {
        int sum = 0;
        String pageNumberAsString = String.valueOf(Math.abs(pageNumber));
        for (int i = 0; i < pageNumberAsString.length(); ++i) {
            StringBuilder digit = new StringBuilder();
            digit.append(pageNumberAsString.charAt(i));
            sum += Integer.valueOf(digit.toString());
        }

        return sum;
    }

    public double getProbabilityOfSum(int randomSum) {
        if (randomSum <= 0)
            throw new IllegalArgumentException("random sum must be greater than zero");
        double probability = 0.0;
        List<Integer> pages = this.sumPageNumbers.get(randomSum);
        if (pages != null) {
            probability = (double) pages.size()/this.pageNumberSum.size();
        }

        return probability;
    }

    public Map<Integer, Integer> getPageNumberSum() {
        return Collections.unmodifiableMap(this.pageNumberSum);
    }

    public Map<Integer, List<Integer>> getSumPageNumbers() {
        return Collections.unmodifiableMap(this.sumPageNumbers);
    }
}