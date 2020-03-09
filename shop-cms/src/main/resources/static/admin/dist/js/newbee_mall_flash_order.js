$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/flashOrders/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'orderNo', index: 'orderNo', width: 50, key: true, hidden: true},
            {label: '订单号', name: 'orderNo', index: 'orderNo', width: 120},
            {label: '订单总价', name: 'totalPrice', index: 'totalPrice', width: 60},
            {label: '订单状态', name: 'payStatus', index: 'payStatus', width: 80, formatter: orderStatusFormatter},
            {label: '支付方式', name: 'payType', index: 'payType', width: 80, formatter: payTypeFormatter},
            {label: '收件人地址', name: 'userAddress', index: 'userAddress', width: 10, hidden: true},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 120},
            // {label: '操作', name: 'createTime', index: 'createTime', width: 120, formatter: operateFormatter}
        ],
        height: 760,
        rowNum: 20,
        rowList: [20, 50, 80],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });


    function orderStatusFormatter(cellvalue) {
        //订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
        if (cellvalue == 0) {
            return "待支付";
        }
        if (cellvalue == 1) {
            return "已支付";
        }
        if (cellvalue == 2) {
            return "配货完成";
        }
        if (cellvalue == 3) {
            return "出库成功";
        }
        if (cellvalue == 4) {
            return "交易成功";
        }
        if (cellvalue == -1) {
            return "手动关闭";
        }
        if (cellvalue == -2) {
            return "超时关闭";
        }
        if (cellvalue == -3) {
            return "商家关闭";
        }
    }

    function payTypeFormatter(cellvalue) {
        //支付类型:0.无 1.支付宝支付 2.微信支付
        if (cellvalue == 0) {
            return "无";
        }
        if (cellvalue == 1) {
            return "支付宝支付";
        }
        if (cellvalue == 2) {
            return "微信支付";
        }
    }

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

});

/**
 * jqGrid重新加载
 */
function reload() {
    initFlatPickr();
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

/**
 * 订单拣货完成
 */
function flashOrderCheckDone() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    var orderNos = '';
    for (i = 0; i < ids.length; i++) {
        var rowData = $("#jqGrid").jqGrid("getRowData", ids[i]);
        if (rowData.payStatus != '已支付') {
            orderNos += rowData.orderId + " ";
        }
    }
    if (orderNos.length > 0 & orderNos.length < 100) {
        swal(orderNos + "订单的状态不是支付成功无法执行配货完成操作", {
            icon: "error",
        });
        return;
    }
    if (orderNos.length >= 100) {
        swal("你选择了太多状态不是支付成功的订单，无法执行配货完成操作", {
            icon: "error",
        });
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要执行配货完成操作吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/flashOrders/checkDone",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("配货完成", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    )
    ;
}

function flashCloseOrder() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要关闭订单吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/flashOrders/close",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("关闭成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    )
    ;
}


function reset() {
    $("#totalPrice").val(0);
    $("#userAddress").val('');
    $('#edit-error-msg').css("display", "none");
}