document.addEventListener('DOMContentLoaded', function() {
    const productImages = document.querySelectorAll('.product-items .product-grid__image img');
    productImages.forEach(function(image) {
        let isHovered = false;

        image.addEventListener('click', function() {
            const parentProduct = image.closest('.product-items');
            const productImage = parentProduct.querySelector('.product-grid__image img.home-banner');
            const hoverImage = parentProduct.querySelector('.product-grid__image img.hover');

            if (!isHovered) {
                productImage.src = hoverImage.src;
                isHovered = true;
            } else {
                productImage.src = parentProduct.querySelector('.product-grid__image img.home-banner').src;
                isHovered = false;
            }
        });
    });
});




