;
(function ($, window, document, undefined) {

  var Magnifier = function (elem) {
    var self = this;
    this.$elem = elem;
    this.$smallBox = this.$elem.find('.small-box');
    this.$smallBox_pic = this.$smallBox.find('img');
    this.$smallBox_mask = this.$smallBox.find('.hover');
    this.$thumbnailBox = this.$elem.find('.thumbnail-box');
    this.$thumbnailBox_btn = this.$thumbnailBox.find('.btn');
    this.$thumbnailBox_prev = this.$thumbnailBox.find('.btn-prev');
    this.$thumbnailBox_next = this.$thumbnailBox.find('.btn-next');
    this.$thumbnailBox_wrapper = this.$thumbnailBox.find('.wrapper');
    this.$thumbnailBox_item = this.$thumbnailBox.find('.item');
    this.$thumbnailBox_pic = this.$thumbnailBox.find('img');
    this.$bigBox = this.$elem.find('.big-box');
    this.$bigBox_pic = this.$bigBox.find('img');
  };

  Magnifier.prototype = {
    moveBigPic: function () { // 改变大图
      var scaleX = this.$smallBox_mask.position().left / (this.$smallBox.width() - this.$smallBox_mask.width());
      var scaleY = this.$smallBox_mask.position().top / (this.$smallBox.height() - this.$smallBox_mask.height());
      var scroll_l = scaleX * (this.$bigBox_pic.width() - this.$bigBox.width());
      var scroll_t = scaleY * (this.$bigBox_pic.height() - this.$bigBox.height());

      this.$bigBox.scrollLeft(scroll_l).scrollTop(scroll_t);
    },

    changeSrouce: function (index, cur_src) { // 大中小三张图设置为同一张图，省去上传时图片压缩的烦恼！
      this.$smallBox_pic.attr('src', cur_src);
      this.$bigBox_pic.attr('src', cur_src);
    },

    setMask: function () {
      this.$smallBox_mask.css({
        width: 150,
        height: 150
      });
    },

    inital: function () { // 初始化
      var self = this;
      var viewNum = 5; //设置每次显示图片的个数量
      var tempLength = 0; //临时变量,当前移动的长度
      var moveNum = 1; //每次移动的数量
      var moveTime = 150; //移动速度,毫秒
      var moveLength = this.$thumbnailBox_item.eq(0).outerWidth(true) * moveNum; //计算每次移动的长度
      var countLength = (this.$thumbnailBox_item.length - viewNum) * moveLength; //计算总长度,总个数*单个长度
      var ov_picL = this.$thumbnailBox_item.length; //获取ul下li的个数

      //当用户未上传图片时,也就是ul下li 的个数为0
      if (ov_picL == 0) {
        self.$thumbnailBox.remove();
        self.$bigBox.remove();
        self.$smallBox_pic.attr('src', 'images/NoneImg.jpg');
        self.$smallBox_mask.remove();
      }
      if (countLength <= 5) {
        $(".thumbnail-box a.btn.btn-prev").addClass("btn-prev_disabled");
        $(".thumbnail-box a.btn.btn-next").addClass("btn_next_disabled");
      } else {
        $(".thumbnail-box a.btn.btn-next").removeClass("btn_next_disabled")
      }
      //点击下一张图
      this.$thumbnailBox_next.click(function () {
        if (tempLength < countLength) {
          if ((countLength - tempLength) > moveLength) {
            self.$thumbnailBox_wrapper.animate({
              marginLeft: "-=" + moveLength + "px"
            }, moveTime);
            tempLength += moveLength;
          } else {
            self.$thumbnailBox_wrapper.animate({
              marginLeft: "-=" + (countLength - tempLength) + "px"
            }, moveTime);
            tempLength += (countLength - tempLength);
          }
        }
        //当图片大于显示个数时，点击下一张按钮即上一张按钮可操作
        if (countLength > viewNum) {
          $(".btn-prev").removeClass("btn_prev_disabled");
        }
        //当下一张图片为最后一张，下一张按钮不可点击
        if (tempLength == countLength) {
          $(".btn-next").addClass("btn_next_disabled")
        }
      });
      //点击上一张图
      this.$thumbnailBox_prev.click(function () {
        if (tempLength > 0) {
          if (tempLength > moveLength) {
            self.$thumbnailBox_wrapper.animate({
              marginLeft: "+=" + moveLength + "px"
            }, moveTime);
            tempLength -= moveLength;
          } else {
            self.$thumbnailBox_wrapper.animate({
              marginLeft: "+=" + tempLength + "px"
            }, moveTime);
            tempLength = 0;
          }
        }
        if (tempLength < countLength) {
          $(".btn-next").removeClass("btn_next_disabled")
        }
        //当上一张图片为第一张，上一张按钮不可点击
        if (tempLength == 0) {
          $(".btn-prev").addClass("btn_prev_disabled")
        }
      });
      this.$thumbnailBox_item.mouseover(function () {
        var cur_src = $(this).attr('data-src');

        self.$thumbnailBox_item.removeClass('item-cur');

        $(this).addClass('item-cur');

        self.changeSrouce($(this).index(), cur_src);
      });

      this.$smallBox.hover(function () {
        self.$bigBox.show();
        self.$smallBox_mask.show();
        self.setMask();

        $(this).mousemove(function (ev) {
          var oEvent = ev || window.event;
          var offset_pos = {
            left: oEvent.clientX - $(this).offset().left - self.$smallBox_mask.width() / 2,
            top: oEvent.clientY - $(this).offset().top - self.$smallBox_mask.height() / 2 + $(window).scrollTop()
          };

          if (offset_pos.left < 0) {
            offset_pos.left = 0;
          } else if (offset_pos.left > $(this).width() - self.$smallBox_mask.width()) {
            offset_pos.left = $(this).width() - self.$smallBox_mask.width();
          }
          if (offset_pos.top < 0) {
            offset_pos.top = 0;
          } else if (offset_pos.top > $(this).height() - self.$smallBox_mask.height()) {
            offset_pos.top = $(this).height() - self.$smallBox_mask.height();
          }

          self.$smallBox_mask.css(offset_pos);

          self.moveBigPic();
        });
      }, function () {
        self.$smallBox_mask.hide();
        self.$bigBox.hide();
      });
    },

    constructor: Magnifier
  };

  $.fn.magnifier = function () {
    var magnifier = new Magnifier(this);

    return magnifier.inital();
  };

})(jQuery, window, document);