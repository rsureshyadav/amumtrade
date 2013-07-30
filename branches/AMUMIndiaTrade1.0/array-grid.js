/*

This file is part of Ext JS 4

Copyright (c) 2011 Sencha Inc

Contact:  http://www.sencha.com/contact

GNU General Public License Usage
This file may be used under the terms of the GNU General Public License version 3.0 as published by the Free Software Foundation and appearing in the file LICENSE included in the packaging of this file.  Please review the following information to ensure the GNU General Public License version 3.0 requirements will be met: http://www.gnu.org/copyleft/gpl.html.

If you are unsure which license is appropriate for your use, please contact the sales department at http://www.sencha.com/contact.

*/
Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*'
]);

Ext.onReady(function() {
    Ext.QuickTips.init();
    
    // setup the state provider, all state information will be saved to a cookie
    Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));

    // sample static data for the store
    var myData = [
['Damodar Industries Ltd.','42.0','3.07','13.68','582.38'],
['Ellora Paper Mills Ltd.','54.2','-2.55','-21.29','31.31'],
['Dish TV India Ltd.','49.15','-32.95','-1.49','1957.82'],
['Jaybharat Textiles & Real Estate Ltd.','49.0', '-48.54', '-1.01', '734.63'],
['EIH Ltd.','46.8','52.49', '0.89', '1133.50'],
['Indian Hotels Company Ltd.', '44.95', '-13.12', '-3.43', '1875.86'],
['Hanung Toys & Textiles Ltd.', '41.15', '0.95', '43.26', '1411.65'],
['Dishman Pharmaceuticals & Chemicals Ltd.', '45.35', '5.79', '7.83', '484.64'],
['Jindal Stainless Ltd.', '46.0', '-9.03', '-5.09', '7845.66'],
['EICL Ltd.', '40.5', '13.72', '2.95', '377.86'],
['Intellivate Capital Ventures Ltd.', '46.2', '54889.97', '0.00', '0.05'],
['Inox Leisure Ltd.', '55.8', '33.61', '1.66', '418.73'],
['India Infoline Ltd.', '47.95', '14.78', '3.24', '418.61'],
['Compulink Systems Ltd.', '56.25', '-62.39', '-0.90', '13.77'],
['Birla Transasia Carpets Ltd.', '50.7', '-11.84', '-4.28', '2.97'],
['Finolex Cables Ltd.', '54.35', '5.72', '9.50', '2270.29'],
['Fluidomat Ltd.', '51.0', '7.05', '7.23', '27.59'],
['Halonix Ltd.', '41.1', '-13.46', '-3.05', '437.26'],
['JBM Auto Ltd.', '53.1', '7.56', '7.02', '395.87'],
['Amarjothi Spinning Mills Ltd.', '50.0', '10.22', '4.89', '129.56'],
['Hindustan Copper Ltd.', '50.0', '14.30', '3.50', '1489.61'],
['Hindustan Hardy Spicer Ltd.', '55.0', '9.39', '5.86', '55.16'],
['Delta Corp Ltd.', '50.1', '-1592.44', '-0.03', '1.17'],
['Bombay Dyeing & Manufacturing Company Ltd.', '46.0', '12.55', '3.67', '2328.19'],
['Jai Corp Ltd.', '41.45', '7.48', '5.54', '617.80'],
['Jyothy Consumer Products Ltd.', '42.1', '-8.10', '-5.20', '450.76'],
['Dena Bank', '55.55', '2.40', '23.15', '8899.39'],
['Easun Reyrolle Ltd.', '43.4', '9.71', '4.47', '276.71'],
['Development Credit Bank Ltd.', '45.05', '11.04', '4.08', '916.10'],
['Diamond Power Infrastructure Ltd.', '51.3', '1.76', '29.13', '1740.38'],
['Indoco Remedies Ltd.', '58.95', '12.73', '4.63', '629.54'],
['Competent Automobiles Co Ltd.', '60.0', '4.94', '12.15', '806.49'],
['Bhagwati Banquets & Hotels Ltd.', '45.95', '418.31', '0.11', '152.55'],
['JSW Energy Ltd.', '41.65', '6.88', '6.05', '6396.45'],
['Brigade Enterprises Ltd.', '50.9', '8.89', '5.72', '776.11'],
['India Home Loan Ltd.', '56.45', '149.20', '0.38', '1.56'],
['Indian Infotech & Software Ltd.', '45.85', '126597.74', '0.00', '0.01'],
['Camson Bio Technologies Ltd.', '47.05', '4.12', '11.43', '112.24'],
['City Union Bank Ltd.', '47.45', '7.94', '5.98', '2188.75'],
['Central Bank of India', '55.4', '5.70', '9.72', '21860.65'],
['Hind Rectifiers Ltd.', '42.3', '6.29', '6.73', '136.13'],
['Hydro S & S Industries Ltd.', '40.25', '1054.85', '0.04', '149.98'],
['Indian Overseas Bank', '42.95', '7.00', '6.14', '20676.73'],
['Horizon Infrastructure Ltd.', '58.35', '9.34', '6.25', '320.03'],
['Cosmo Films Ltd.', '50.1', '2.14', '23.44', '923.05'],
['Jyoti Ltd.', '58.0', '13.36', '4.34', '506.19'],
['Bajaj Steel Industries Ltd.', '60.0', '1.97', '30.43', '313.39'],
['Jetking Infotrain Ltd.', '42.85', '7.99', '5.37', '32.54'],
    ];

    /**
     * Custom function used for column renderer
     * @param {Object} val
     */
    function change(val) {
        if (val > 0) {
            return '<span style="color:green;">' + val + '</span>';
        } else if (val < 0) {
            return '<span style="color:red;">' + val + '</span>';
        }
        return val;
    }

    /**
     * Custom function used for column renderer
     * @param {Object} val
     */
    function pctChange(val) {
        if (val > 0) {
            return '<span style="color:green;">' + val + '%</span>';
        } else if (val < 0) {
            return '<span style="color:red;">' + val + '%</span>';
        }
        return val;
    }

    // create the data store
    var store = Ext.create('Ext.data.ArrayStore', {
        fields: [
           {name: 'name'},
           {name: 'price',      type: 'float'},
           {name: 'perRatio',      type: 'float'},
           {name: 'eps',      type: 'float'},
           {name: 'revenue',      type: 'float'}
        ],
        data: myData
    });

    // create the Grid
    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        stateful: true,
        stateId: 'stateGrid',
        columns: [
            {
                text     : 'Name',
                flex     : 1,
                sortable : true,
                dataIndex: 'name'
            },
            {
                text     : 'Price',
                width    : 75,
                sortable : true,
            //    renderer : 'usMoney',
                dataIndex: 'price'
            },
            {
                text     : 'P/E Ratio',
                width    : 75,
                sortable : true,
                renderer : 'perRatio',
                dataIndex: 'perRatio'
            },
            {
                text     : 'EPS',
                width    : 75,
                sortable : true,
                renderer : 'eps',
                dataIndex: 'eps'
            },
            {
                text     : 'Revenue',
                width    : 75,
                sortable : true,
                renderer : 'revenue',
                dataIndex: 'revenue'
            },
            
        ],
        height: 350,
        width: 600,
        title: 'Array Grid',
        renderTo: 'grid-example',
        viewConfig: {
            stripeRows: true
        }
    });
});

