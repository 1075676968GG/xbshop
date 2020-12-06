/*
	JavaScript For Responsive Bootstrap Carousel

    Author: Razboynik
    Author URI: http://filwebs.ru
    Description: Bootstrap Carousel Effect Ken Burns

*/
jQuery(document).ready(function ($) {
    $(".scroll").click(function (event) {
        event.preventDefault();
        $('html,body').animate({
            scrollTop: $(this.hash).offset().top
        }, 1000);
    });
});
$(document).ready(function () {
    var defaults = {
        containerID: 'toTop', // fading element id
        containerHoverID: 'toTopHover', // fading element hover id
        scrollSpeed: 1200,
        easingType: 'linear'
    };
    $().UItoTop({
        easingType: 'easeOutQuart'
    });


    $('.header-two').scrollToFixed();
    var summaries = $('.summary');
    summaries.each(function (i) {
        var summary = $(summaries[i]);
        var next = summaries[i + 1];
        summary.scrollToFixed({
            marginTop: $('.header-two').outerHeight(true) + 10,
            zIndex: 999
        });
    });

    function getTime() {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hh = date.getHours();
        hh = hh < 10 ? '0' + hh : hh;
        var mm = date.getMinutes();
        mm = mm < 10 ? '0' + mm : mm;
        var ss = date.getSeconds();
        ss = ss < 10 ? '0' + ss : ss;
        return year + "/" + month + "/" + day + "/  " + hh + ":" + mm + ":" + ss;
    }

    $(".my-account a").text(getTime());
    setInterval(function () {
        $(".my-account a").text(getTime())
    }, 1000)


    //单价*数量=总价
    //数量+
    $(".goods-add").on("click",function () {
        var num=parseInt($(this).parent().siblings(".goods-num").val());
        $(this).parent().siblings(".goods-num").val(++num);

        var price=parseFloat($(".single-price").text().slice(1));
        $(this).parents(".goods-counts").siblings(".goods-money-count").children("span").text("￥"+(price*num).toFixed(2))
        getSum();
    });
    //数量-
    $(".goods-sub").on("click",function () {
        var num=parseInt($(this).parent().siblings(".goods-num").val());
        if (--num <= 0) {
            num=1;
        }
        $(this).parent().siblings(".goods-num").val(num);
        var price=parseFloat($(".single-price").text().slice(1));
        $(this).parents(".goods-counts").siblings(".goods-money-count").children("span").text("￥"+(price*num).toFixed(2))
        getSum();
    });
    getSum();
    //全部商品 金额
    function getSum() {
        var moneyList=$(".goods-totalMoney");
        var money=0;
        $.each(moneyList,function (index, item) {
            money+=parseFloat($(item).text().slice(1));
        });
        $(".goodsList-totalMoney").val("￥"+money.toFixed(2))
    }


});

$(function ($) {

    /*-----------------------------------------------------------------*/
    /* ANIMATE SLIDER CAPTION
    /* Demo Scripts for Bootstrap Carousel and Animate.css article on SitePoint by Maria Antonietta Perna
    /*-----------------------------------------------------------------*/
    "use strict";

    function doAnimations(elems) {
        //Cache the animation end event in a variable
        var animEndEv = 'webkitAnimationEnd animationend';
        elems.each(function () {
            var $this = $(this),
                $animationType = $this.data('animation');
            $this.addClass($animationType).one(animEndEv, function () {
                $this.removeClass($animationType);
            });
        });
    }

    //Variables on page load
    var $immortalCarousel = $('.animate_text'),
        $firstAnimatingElems = $immortalCarousel.find('.item:first').find("[data-animation ^= 'animated']");
    //Initialize carousel
    $immortalCarousel.carousel();
    //Animate captions in first slide on page load
    doAnimations($firstAnimatingElems);
    //Other slides to be animated on carousel slide event
    $immortalCarousel.on('slide.bs.carousel', function (e) {
        var $animatingElems = $(e.relatedTarget).find("[data-animation ^= 'animated']");
        doAnimations($animatingElems);
    });

});