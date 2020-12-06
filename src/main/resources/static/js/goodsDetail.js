$(function () {
    $(".sub").on("click", function () {
        var num = $(this).parent().siblings(".num").val();
        num--;
        if (num <= 1) {
            num = 1;
        }
        $(this).parent().siblings(".num").val(num);
        getSum();
    });
    $(".add").on("click", function () {
        var num = $(this).parent().siblings(".num").val();
        $(this).parent().siblings(".num").val(++num);
        getSum();
    });
    getSum();

    function getSum() {
        $(".totalMoney").html("$" + parseFloat($(".goodsMoney").text().slice(1) * $(".num").val()).toFixed(2))
    };

    $(".num").change(function () {
        getSum();
    })
})