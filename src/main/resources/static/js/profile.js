$(function () {
    $("#case3").bMap({
        name: "callback",
        callback: function (address, point) {
            console.log(JSON.stringify(address));
        }
    });
    $(".bMap #Map_input_callback").addClass("form__input");


    $(".user__img").click(function () {
        $(".uploadImg").click();
    });

    $(".table-address tr td button").on("click",function () {
        $(this).addClass("btn btn-danger");
        $(this).parents("tr").siblings().find("button").removeClass("btn btn-danger")
    });
    
    $(".del-address").on("click",function (ev) {
        var that = $(ev.target);
        $(".deleteSure").on("click", function () {
            $('#deleteItemTip').modal('hide');
            that.parents("tr").remove();
        })
    })
})