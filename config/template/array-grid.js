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
	@AMUMTradeMetricData
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
        //height: 500,
        //width: '100%',
        title: 'AMUMTrade Key Metric...',
        renderTo: 'grid-example',
        viewConfig: {
            stripeRows: true
        }
    });
});

