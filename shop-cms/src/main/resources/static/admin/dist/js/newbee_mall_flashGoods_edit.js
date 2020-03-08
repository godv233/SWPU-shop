//KindEditor变量
var editor;

$(function () {

    //详情编辑器
    editor = KindEditor.create('textarea[id="editor"]', {
        items: ['source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
            'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
            'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
            'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
            'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
            'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'multiimage',
            'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
            'anchor', 'link', 'unlink'],
        uploadJson: '/admin/upload/file',
        filePostName: 'file'
    });

    new AjaxUpload('#uploadGoodsCoverImg', {
        action: '/admin/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                $("#goodsCoverImg").attr("src", r.data);
                $("#goodsCoverImg").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});

$('#cancelFlashButton').click(function () {
    window.location.href = "/admin/flashGoods";
});

$('#confirmFlashButton').click(function () {
    var goodsId = $('#goodsId').val();
    var flashPrice = $('#flashPrice').val();
    var stockCount = $('#stockCount').val();
    console.log(goodsId);
    console.log(flashPrice);
    console.log(stockCount);
    if (isNull(flashPrice)||flashPrice<0) {
        swal("请输入商品秒杀价格", {
            icon: "error",
        });
        return;
    }
    if (isNull(stockCount)||stockCount<0) {
        swal("请输入秒杀商品的库存", {
            icon: "error",
        });
        return;
    }
    $.ajax({
        type:'post',
        url:'/admin/flashGoods/update',
        data:{
            "goodsId":goodsId,
            "flashPrice":flashPrice,
            "stockCount":stockCount
        },
        contentType: 'application/x-www-form-urlencoded',
        success:function(result){
            if (result.resultCode == 200) {
                window.location.href = "/admin/flashGoods";
            }

        }
    });
});