$(function () {

    $(".goods-operate .item-delete").on("click", function (ev) {
        var that = $(ev.target);
        $(".deleteSure").on("click", function () {
            that.parents(".goods-item").remove();
            $('#deleteItemTip').modal('hide');
            var moneyList = $(".goods-totalMoney");
            var money = 0;
            $.each(moneyList, function (index, item) {
                money += parseFloat($(item).text().slice(1));
            });
            $(".goodsList-totalMoney").val("￥" + money.toFixed(2))
        })
    })

})