Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*'
]);

Ext.onReady(function() {
    Ext.QuickTips.init();
    
    Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));

    var myData = [
['1','AEY','2.56','0.19','13.42','10.86%','5.58%','5.23%','3.43','Consumer Services','Office Equipment/Supplies/Services'],
['2','ASRV','3.0','0.20','15.36','13.53%','0.41%','3.79%','2.48','Finance','Major Banks'],
['3','BAMM','2.59','0.05','55.78','1.00%','1.06%','0.71%','32.77','Consumer Services','Other Specialty Stores'],
['4','BSQR','2.82','0.02','133.19','0.53%','0.73%','0.87%','8.79','Miscellaneous','Business Services'],
['5','CAMT','2.32','0.02','136.52','4.72%','2.43%','0.84%','2.83','Capital Goods','Electronic Components'],
['6','CALI','2.9301','0.54','5.34','1.23%','2.11%','3.37%','160.11','Consumer Services','Motor Vehicles'],
['7','CHOP','1.79','0.20','8.58','10.47%','2.38%','3.74%','4.12','Capital Goods','Steel/Iron Ore'],
['8','CHLN','2.08','0.50','4.03','11.07%','2.30%','11.36%','4.91','Basic Industries','Homebuilding'],
['9','CCCL','2.16','1.30','1.68','19.14%','8.69%','12.33%','9.54','Capital Goods','Building Materials'],
['10','CREG','2.78','0.10','27.81','10.08%','0.46%','3.78%','0.32','Miscellaneous','Business Services'],
['11','COGO','2.26','0.11','20.36','1.44%','1.80%','3.37%','22.04','Capital Goods','Electrical Products'],
['12','CRWN','2.76','0.31','9.15','38.45%','8.74%','36.74%','0.98','Consumer Services','Television Services'],
['13','DHRM','1.98','0.70','2.83','20.53%','7.49%','10.00%','4.67','Health Care','Medical/Dental Instruments'],
['14','DYNT','2.3401','0.05','46.27','1.18%','1.47%','2.32%','11.86','n/a','n/a'],
['15','EDUC','3.26','0.13','24.85','3.09%','2.70%','3.80%','6.31','Consumer Durables','Consumer Specialties'],
['16','ENZN','2.01','0.02','134.67','31.02%','2.98%','0.49%','0.91','Health Care','Biotechnology: Biological Products (No Diagnostic Substances)'],
['17','EMMS','2.56','1.24','2.13','13.64%','5.48%','22.60%','4.99','Consumer Services','Broadcasting'],
['18','FLL','2.9','0.14','21.90','5.87%','3.68%','3.20%','7.20','Consumer Services','Hotels/Resorts'],
['19','GLDC','3.48','0.10','35.88','1.59%','N/A','4.60%','11.71','Consumer Non-Durables','Packaged Foods'],
['20','GPRC','1.75','1.17','1.45','20.85%','20.72%','28.72%','7.48','Basic Industries','Containers/Packaging'],
['21','HIHO','1.9','0.12','16.05','1.62%','1.39%','3.73%','5.80','Capital Goods','Metal Fabrications'],
['22','HDSN','2.75','0.56','5.09','28.02%','21.91%','49.74%','2.68','Consumer Durables','Industrial Specialties'],
['23','HPOL','1.87','0.08','22.93','5.24%','7.89%','45.25%','2.50','Consumer Services','Professional Services'],
['24','ICCC','3.459','0.05','72.41','5.26%','1.63%','1.49%','1.83','Health Care','Biotechnology: In Vitro & In Vivo Diagnostic Substances'],
['25','ISIG','2.36','0.03','87.04','4.01%','1.84%','1.40%','1.73','Consumer Services','Advertising'],
['26','III','2.67','0.06','45.42','4.56%','4.09%','5.73%','5.43','Consumer Services','Professional Services'],
['27','LLEN','3.36','0.90','3.70','20.31%','9.66%','19.52%','5.64','Energy','Coal Mining'],
['28','LFVN','2.61','0.10','26.46','12.21%','34.97%','43.99%','1.81','Health Care','Major Pharmaceuticals'],
['29','LPTH','1.73','0.06','31.23','1.00%','0.97%','14.64%','1.03','Technology','Semiconductors'],
['30','LIOX','3.25','0.11','29.41','3.08%','5.09%','11.05%','7.71','Miscellaneous','Business Services'],
['31','MAXY','2.5','0.74','3.36','69.77%','10.87%','17.87%','1.10','Health Care','Biotechnology: Commercial Physical & Biological Resarch'],
['32','LOAN','1.58','0.10','15.80','45.92%','3.77%','5.31%','0.38','Finance','Finance Companies'],
['33','MTSL','1.74','0.30','5.97','16.24%','12.14%','27.46%','2.97','Public Utilities','Telecommunications Equipment'],
['34','MNDO','1.82','0.18','10.28','17.41%','7.02%','15.48%','1.03','Technology','EDP Services'],
['35','MBND','3.2','0.14','23.28','1.02%','1.46%','7.54%','14.11','Public Utilities','Telecommunications Equipment'],
['36','NEWT','2.1','0.16','13.12','11.35%','6.14%','8.71%','3.76','Miscellaneous','Business Services'],
['37','OBCI','2.77','0.23','12.48','9.53%','8.91%','12.25%','3.78','Consumer Durables','Specialty Chemicals'],
['38','SEED','1.53','0.13','11.29','4.61%','1.29%','6.83%','3.82','Consumer Non-Durables','Farming/Seeds/Milling'],
['39','PRXI','1.69','0.04','47.78','5.59%','3.32%','7.71%','0.76','Consumer Services','Services-Misc. Amusement & Recreation'],
['40','QCCO','2.68','0.13','20.76','9.47%','8.19%','5.85%','10.37','Finance','Investment Bankers/Brokers/Service'],
['41','RAND','2.94','0.23','13.10','48.92%','3.10%','6.13%','0.46','n/a','n/a'],
['42','RELV','2.2601','0.08','36.34','1.82%','2.92%','6.59%','5.41','Health Care','Major Pharmaceuticals'],
['43','FRBK','3.25','0.13','27.56','14.95%','0.36%','4.84%','1.51','Finance','Major Banks'],
['44','RVSB','2.76','0.27','9.70','34.12%','0.76%','7.80%','1.87','Finance','Savings Institutions'],
['45','SPU','2.26','0.75','3.05','28.27%','10.65%','13.55%','4.02','Consumer Non-Durables','Packaged Foods'],
['46','SORL','2.66','0.60','4.38','7.51%','3.78%','7.06%','9.79','Capital Goods','Auto Parts:O.E.M.'],
['47','SGOC','1.88','0.29','6.22','4.93%','N/A','6.51%','10.94','Technology','Radio And Television Broadcasting And Communications Equipment'],
['48','SMTX','1.9','0.38','5.01','2.80%','3.98%','15.44%','17.72','Technology','Electrical Products'],
['49','SGRP','2.15','0.13','16.25','3.61%','9.55%','24.71%','5.31','Miscellaneous','Business Services'],
['50','SUTR','1.76','0.34','5.19','4.40%','3.43%','6.22%','14.84','Capital Goods','Steel/Iron Ore'],
['51','SYNC','3.26','0.10','32.73','3.60%','3.81%','5.47%','4.45','Technology','Computer Software: Programming Data Processing'],
['52','TSYS','2.53','0.18','14.24','3.83%','2.83%','7.05%','7.90','Technology','Computer Software: Prepackaged Software'],
['53','VTNR','2.7799','0.20','13.38','2.41%','5.67%','19.48%','9.47','Public Utilities','Environmental Services'],
['54','TISA','2.899','0.25','11.29','7.31%','6.79%','24.39%','2.63','Technology','Computer peripheral equipment'],
['55','GROW','2.71','0.01','293.00','1.81%','0.54%','0.36%','1.29','Finance','Investment Managers'],
['56','WRES','2.88','0.20','14.04','17.08%','3.90%','7.76%','1.74','Energy','Oil & Gas Production'],


    ];


    var store = Ext.create('Ext.data.ArrayStore', {
        fields: [
           {name: 'sno',      type: 'float'},
           {name: 'symbol'},
           {name: 'price',      type: 'float'},
           {name: 'dilutedEPS',      type: 'float'},
           {name: 'peRatio',      type: 'float'},
           {name: 'operatingMargin',      type: 'float'},
           {name: 'returnOnAssert',      type: 'float'},
           {name: 'returnOnEquity',      type: 'float'},
           {name: 'revenuePerShare',      type: 'float'},
           {name: 'sector'},
           {name: 'industry'}

           ],
        data: myData
    });

    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        stateful: true,
        stateId: 'stateGrid',
        columns: [
           {
                 text     : 'S.No',
                 width    : 75,
                 //flex     : 1,
                 sortable : false,
                 dataIndex: 'sno'
            },
            {
                text     : 'Symbol',
                width    : 75,
                 //flex     : 1,
                sortable : false,
                dataIndex: 'symbol'
            },
            {
                text     : 'Price',
                width    : 75,
                sortable : true,
                renderer : 'usMoney',
                dataIndex: 'price'
            },   {
                text     : 'EPS',
                width    : 75,
                sortable : true,
                dataIndex: 'dilutedEPS'
            },   {
                text     : 'P/E Ratio',
                width    : 100,
                sortable : true,
                dataIndex: 'peRatio'
            },   {
                text     : 'Operating Margin',
                width    : 100,
                sortable : true,
                dataIndex: 'operatingMargin'
            },   {
                text     : 'Return On Assert',
                width    : 100,
                sortable : true,
                dataIndex: 'returnOnAssert'
            },   {
                text     : 'Return On Equity',
                width    : 100,
                sortable : true,
                dataIndex: 'returnOnEquity'
            },   {
                text     : 'Revenue Per Share',
                width    : 125,
                sortable : true,
                dataIndex: 'revenuePerShare'
            },   {
                text     : 'Sector',
                width    : 300,
                sortable : true,
                dataIndex: 'sector'
            },   {
                text     : 'Industry',
                width    : 300,
                sortable : true,
                dataIndex: 'industry'
            },
            
        ],
        height: 500,
        width: '100%',
        title: 'AMUMTrade Key Metric...',
        renderTo: 'grid-example',
        viewConfig: {
            stripeRows: true
        }
    });
});

