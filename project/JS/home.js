// handle slider home page
const listImgs = document.querySelector('.list-imgs');
const imgs = document.getElementsByClassName('img-slide');
const btnLeft = document.querySelector('.btn-left');
const btnRight = document.querySelector('.btn-right');
const length = imgs.length;
let current = 0;
const changeSlide = () =>{
    if(current==length-1){
        current =0;
        let width = imgs[0].offsetWidth; 
        listImgs.style.transform = `translateX(${width  * -1*current}px)`;
        document.querySelector('.active').classList.remove('active');
        document.querySelector('.item-'+current).classList.add('active');
    }else {
        current++;
        let width = imgs[0].offsetWidth; 
        listImgs.style.transform = `translateX(${width  * -1*current}px)`;
        document.querySelector('.active').classList.remove('active');
        document.querySelector('.item-'+current).classList.add('active');
    }
};
let eventChangeSlide = setInterval(changeSlide, 4000);

btnRight.addEventListener('click' , () => {
    clearInterval(eventChangeSlide);
    changeSlide();
    eventChangeSlide = setInterval(changeSlide, 4000);
});
btnLeft.addEventListener('click' , () => {
    clearInterval(eventChangeSlide);
    if(current==0){
        current = length-1;
        let width = imgs[0].offsetWidth; 
        listImgs.style.transform = `translateX(${width  * -1*current}px)`;
        document.querySelector('.active').classList.remove('active');
        document.querySelector('.item-'+current).classList.add('active');
    }else {
        current--;
        let width = imgs[0].offsetWidth; 
        listImgs.style.transform = `translateX(${width  * -1*current}px)`;
        document.querySelector('.active').classList.remove('active');
        document.querySelector('.item-'+current).classList.add('active');
    }
    eventChangeSlide = setInterval(changeSlide, 4000);;
});
// handle button "xêm thêm" section-bestseller
const btnShowMore = document.querySelector('.more');
const hide = document.querySelector('.hide');
const btnHide = document.querySelector('.hide-btn');
const productsNone = document.querySelectorAll('.product-none'); // Dùng querySelectorAll để lấy tất cả các phần tử có class .product-none

btnShowMore.onclick = function() {
    productsNone.forEach(product => {
        product.style.display = 'block'; // Đặt display thành 'block' cho từng phần tử
        btnShowMore.style.display = 'none';
        hide.style.display = 'flex';
    });
};
btnHide.onclick = function() {
        productsNone.forEach(product => {
        product.style.display = 'none'; // Đặt display thành 'block' cho từng phần tử
        btnShowMore.style.display = 'flex';
        hide.style.display = 'none';
    });
};


