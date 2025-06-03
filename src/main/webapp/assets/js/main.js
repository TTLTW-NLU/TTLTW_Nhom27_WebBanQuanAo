let captions = {
  0: {
    title: "Đẳng cấp thời trang - Đồng hành phong cách!",
    description: "Quần áo chất lượng cao, hỗ trợ tư vấn quần áo miễn phí.",
    link: "about.jsp"
  },
  1: {
    title: "Ưu đãi đặc biệt - Chọn ngay hôm nay!",
    description: "Miễn phí đổi trả trong 7 ngày, giao hàng nhanh toàn quốc.",
    link: "sale.jsp"
  }
};

const title = $('#customCarousel .carousel_caption h5');
const des = $('#customCarousel .carousel_caption p');
const link = $('#customCarousel .carousel_caption a');

const updateCarouselDescription = function (event) {
  const slideIndex = event.to;
  link.attr('href', captions[slideIndex].link);

  $('#customCarousel .carousel_caption h5').css({ opacity: 0, transition: 'opacity 0.3s' });
  $('#customCarousel .carousel_caption p').css({ opacity: 0, transition: 'opacity 0.3s' });

  setTimeout(() => {
    title.text(captions[slideIndex].title);
    des.text(captions[slideIndex].description);

    $('#customCarousel .carousel_caption h5').css({ opacity: 1 });
    $('#customCarousel .carousel_caption p').css({ opacity: 1 });
  }, 300);
};

const initializeCarouselCaption = () => {
  const initialIndex = 0;
  title.text(captions[initialIndex].title);
  des.text(captions[initialIndex].description);
  link.attr('href', captions[initialIndex].link);
};

$(document).ready(() => {
  initializeCarouselCaption();
  $('#customCarousel').on('slide.bs.carousel', updateCarouselDescription);
});