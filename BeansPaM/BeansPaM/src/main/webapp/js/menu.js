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
    let closeButton = document.querySelector('.menu-close');

    // 닫기 버튼 추가 함수
    const addCloseButton = () => {
        if (!closeButton) {
            closeButton = document.createElement('span');
            closeButton.classList.add('menu-close');
            closeButton.innerHTML = '&times;';
            document.body.appendChild(closeButton);

            closeButton.addEventListener('click', function() {
                closeMenu();
            });
        }
    };

    // 메뉴 닫기 함수
    const closeMenu = () => {
        mainMenu.classList.remove('open');
        menuToggle.style.display = 'block'; // 메뉴 닫으면 햄버거 버튼 다시 보이기
        closeButton.style.display = 'none'; // 닫기 버튼 숨기기
    };

    // 메뉴 열기/닫기 처리
    const toggleMenu = () => {
        mainMenu.classList.toggle('open');
        if (mainMenu.classList.contains('open')) {
            menuToggle.style.display = 'none'; // 메뉴 열리면 햄버거 버튼 숨기기
            closeButton.style.display = 'block'; // 닫기 버튼 표시
        } else {
            closeMenu();
        }
    };

    // 클릭 이벤트 추가
    const addClickEvent = () => {
        menuToggle.addEventListener('click', toggleMenu);
        menuItems.forEach(menuItem => {
            const menuLink = menuItem.querySelector('.menu-title');
            menuLink.addEventListener('click', function(e) {
                e.preventDefault();
                menuItem.classList.toggle('open');
            });
        });
    };

    // 초기 화면 크기 처리
    const initMenuState = () => {
        if (window.innerWidth <= 800) {
            menuToggle.style.display = 'block'; // 모바일 환경에서 메뉴 버튼 보이기
            addClickEvent(); // 클릭 이벤트 추가
        } else {
            menuToggle.style.display = 'none'; // 데스크탑 환경에서는 메뉴 버튼 숨기기
        }
    };

    // 리사이즈 이벤트 처리
    window.addEventListener('resize', initMenuState);

    // 초기화 실행
    addCloseButton(); // 닫기 버튼 추가
    initMenuState(); // 초기 상태 설정
}
