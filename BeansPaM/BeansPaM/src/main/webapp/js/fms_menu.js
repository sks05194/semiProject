// 메뉴에 대한 css 추가
let link = document.createElement('link');
link.rel = 'stylesheet';
link.href = '/BeansPaM/css/fms_menu.css';
document.head.appendChild(link);

// header가 없을 경우 추가
if (document.getElementsByTagName('header').length == 0) {
    let header = document.createElement('header');
    document.body.insertAdjacentElement('afterbegin', header);
}

// footer가 없을 경우 추가
if (document.getElementsByTagName('footer').length == 0) {
    let footer = document.createElement('footer');
    document.body.insertAdjacentElement('beforeend', footer);
}

// header 메뉴 추가
fetch('/BeansPaM/pages/fms_header_menu.html')
    .then(response => {
        if (!response.ok) {
            throw new Error('네트워크 응답에 문제가 있습니다.');
        }
        return response.text();
    })
    .then(data => {
        document.getElementsByTagName('header')[0].innerHTML = data;
        fms_menu_js();
    })
    .catch(error => {
        console.error('fms_header_menu.html을 불러오는 중에 문제가 발생했습니다:', error);
    });

// divider(물결 무늬) 추가
fetch('/BeansPaM/pages/divider.html')
    .then(response => {
        if (!response.ok) {
            throw new Error('네트워크 응답에 문제가 있습니다.');
        }
        return response.text();
    })
    .then(data => {
        document.getElementsByTagName('header')[0].insertAdjacentHTML('afterend', data);
    })
    .catch(error => {
        console.error('fms_header_menu.html을 불러오는 중에 문제가 발생했습니다:', error);
    });

// footer 메뉴 추가
fetch('/BeansPaM/pages/fms_footer_menu.html')
    .then(response => {
        if (!response.ok) {
            throw new Error('네트워크 응답에 문제가 있습니다.');
        }
        return response.text();
    })
    .then(data => {
        document.getElementsByTagName('footer')[0].innerHTML = data;
    })
    .catch(error => {
        console.error('fms_footer_menu.html을 불러오는 중에 문제가 발생했습니다:', error);
    });

// header 메뉴에 click 이벤트 추가
function fms_menu_js() {
    $('.intro li').click(function () {
        $(this).addClass('active');
        $(this).siblings().removeClass('active');
        var result = $(this).attr('data-alt');
        $('.title div').removeClass('active');
        $('#' + result).addClass('active');
    });

    $('#ham').click(function () {
        $('.title div').removeClass('active');
    });
}