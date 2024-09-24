// 메뉴에 대한 CSS 추가 (중복 방지)
if (!document.querySelector('link[href="/BeansPaM/css/menu.css"]')) {
    let link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = '/BeansPaM/css/menu.css';
    document.head.appendChild(link);
}

// 헤더 요소 체크 후 없으면 추가
if (!document.querySelector('header')) {
    let header = document.createElement('header');
    document.body.insertAdjacentElement('afterbegin', header);
}

// JSP 파일 추가
fetch('/BeansPaM/pages/menu.jsp')
    .then(response => {
        if (!response.ok) {
            throw new Error('네트워크 응답에 문제가 있습니다.');
        }
        return response.text();
    })
    .then(data => {
        let header = document.querySelector('header');
        header.insertAdjacentHTML('afterbegin', data);
        menu_js(); // 메뉴 JS 함수 호출
    })
    .catch(error => {
        console.error('menu.jsp를 불러오는 중에 문제가 발생했습니다:', error);
    });

// 메뉴 JS 함수 정의
function menu_js() {
    const menuToggle = document.querySelector('.menu-toggle');
    const mainMenu = document.querySelector('.main-menu');
    const menuItems = document.querySelectorAll('.has-submenu');

    // 메뉴 토글 버튼 클릭 시 메뉴 열고 닫기 (모바일)
    if (menuToggle) {
        menuToggle.addEventListener('click', function() {
            mainMenu.classList.toggle('open');
        });
    }

    // 서브 메뉴 열고 닫기 (모바일)
    menuItems.forEach(function(menuItem) {
        const menuLink = menuItem.querySelector('a');

        if (menuLink) {
            menuLink.addEventListener('click', function(e) {
                if (window.innerWidth <= 767) {
                    e.preventDefault();
                    menuItem.classList.toggle('open');
                }
            });
        }
    });
}
