// ===================================================== Products =====================================================

$(document).ready(function () {
    $('#mainCarousel').on('slide.bs.carousel', function (e) {
        var activeIndex = $(e.relatedTarget).index();

        $('#section_products .thumbnail-list img').removeClass('active');

        $('#section_products .thumbnail-list img').eq(activeIndex).addClass('active');
    });

    $('#section_products .thumbnail-list img').click(function () {
        var index = $(this).index();
        $('#mainCarousel').carousel(index);
    });

    $(document).click(function (event) {
        if (!$(event.target).closest('#section_products .thumbnail-list img').length && !$(event.target).closest('#mainCarousel img').length) {
            $('#section_products .thumbnail-list img').removeClass('active');
        }
    });

    $('#mainCarousel').on('slid.bs.carousel', function () {
        var currentIndex = $('#mainCarousel .carousel-item.active').index();
        $('#section_products .thumbnail-list img').removeClass('active');
        $('#section_products .thumbnail-list img').eq(currentIndex).addClass('active');
    });
});

$('#mainCarousel').carousel({
    interval: false
});

// $('#section_products .choose_color span').click(function () {
//     var colorIndex = $(this).index();
//
//     $('#mainCarousel').carousel(colorIndex);
// });

// ===================================================== Similar Section =====================================================

$('#carousel_similar').carousel({
    interval: false
});

// ===================================================== Pop Up =====================================================

// const openPopupBtn = document.getElementById("open-popup");
// const closePopupBtn = document.getElementById("close-popup");
// const popup = document.getElementById("popup");
// const overlay = document.getElementById("popup-overlay");
//
// openPopupBtn.addEventListener("click", (e) => {
//     e.preventDefault();
//     popup.style.display = "block";
//     overlay.style.display = "block";
// });
//
// const closePopup = () => {
//     popup.style.display = "none";
//     overlay.style.display = "none";
// };
//
// closePopupBtn.addEventListener("click", closePopup);
// overlay.addEventListener("click", closePopup);

// ===================================================== Quantity =====================================================

// const decreaseBtn = document.getElementById("decrease-btn");
// const increaseBtn = document.getElementById("increase-btn");
// const quantityInput = document.getElementById("quantity-input");
//
// increaseBtn.addEventListener("click", () => {
//     let currentValue = parseInt(quantityInput.value, 10);
//     quantityInput.value = currentValue + 1;
// });
//
// decreaseBtn.addEventListener("click", () => {
//     let currentValue = parseInt(quantityInput.value, 10);
//     if (currentValue > 1) {
//         quantityInput.value = currentValue - 1;
//     }
// });