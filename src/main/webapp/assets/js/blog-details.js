document.addEventListener("DOMContentLoaded", function () {
    var toggleButton = document.querySelector('.ez-toc-toggle ');  
    var tocList = document.querySelector('.ez-toc-list');  
    var container = document.getElementById('ez-toc-container');  

    tocList.style.display = 'none';
    tocList.style.height = '0';
    tocList.style.opacity = '0';

    toggleButton.addEventListener('click', function (e) {
        e.preventDefault(); 

   
        if (tocList.style.display === 'none' || tocList.style.display === '') {
    
            tocList.style.display = 'block'; 
            tocList.style.height = tocList.scrollHeight + "px"; 
            tocList.style.opacity = '1';  

            container.classList.add('eztoc-open');
            container.classList.remove('eztoc-closed');
        } else {

            tocList.style.height = '0'; 
            tocList.style.opacity = '0';  
            
            setTimeout(function() {
                tocList.style.display = 'none';  
            }, 300); 

            container.classList.add('eztoc-closed');
            container.classList.remove('eztoc-open');
        }
    });
});
