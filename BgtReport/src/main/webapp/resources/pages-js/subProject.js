var store = Ext.create('Ext.data.ArrayStore', {
    fields: ['company', 'price', 'change'],
    data: [
        ['3m Co',                               71.72, 0.02],
        ['Alcoa Inc',                           29.01, 0.42],
        ['Altria Group Inc',                    83.81, 0.28],
        ['American Express Company',            52.55, 0.01],
        ['American International Group, Inc.',  64.13, 0.31],
        ['AT&T Inc.',                           31.61, -0.48]
    ]
});

var grid = Ext.create('Ext.grid.Panel', {
    title: 'Array Grid',
    store: store,
    columns: [
        {text: 'Company', flex: 1, dataIndex: 'company'},
        {text: 'Price', width: 75, dataIndex: 'price'},
        {text: 'Change', width: 75, dataIndex: 'change'}
    ],
    height: 200,
    width: 400,
    renderTo: Ext.getBody()
});

grid.getView().on('render', function(view) {
    view.tip = Ext.create('Ext.tip.ToolTip', {
        // The overall target element.
        target: view.el,
        // Each grid row causes its own seperate show and hide.
        delegate: view.itemSelector,
        // Moving within the row should not hide the tip.
        trackMouse: true,
        // Render immediately so that tip.body can be referenced prior to the first show.
        renderTo: Ext.getBody(),
        listeners: {
            // Change content dynamically depending on which element triggered the show.
            beforeshow: function updateTipBody(tip) {
                tip.update('Over company "' + view.getRecord(tip.triggerElement).get('company') + '"');
            }
        }
    });
});