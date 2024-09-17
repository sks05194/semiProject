// 메뉴에 대한 css 추가
let link = document.createElement('link');
link.rel = 'stylesheet';
link.href = '/BeansPaM/css/fms_menu.css';
document.head.appendChild(link);

// 메뉴 추가
fetch('/BeansPaM/menu/fms_menu.html')
    .then(response => {
        if (!response.ok) {
            throw new Error('네트워크 응답에 문제가 있습니다.');
        }
        return response.text();
    })
    .then(data => {
        document.getElementsByTagName('header')[0].innerHTML = data;
    })
    .catch(error => {
        console.error('menu.html을 불러오는 중에 문제가 발생했습니다:', error);
    });