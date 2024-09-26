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
	const menuButton = document.querySelector('.menu-toggle'); // 메뉴 버튼
	const mainMenu = document.querySelector('.main-menu'); // 메뉴들을 감싼 구역
	const menuItems = document.querySelectorAll('.has-submenu'); // 서브 메뉴가 있는 구역
	let closeButton = document.querySelector('.menu-close');

	// 화면 크기에 따른 메뉴 상태 초기화
	const initMenuState = () => {
		if (window.innerWidth > 800) {
			setWebMenu();  // 웹 환경 설정
		} else {
			setMobileMenu();  // 모바일 환경 설정
		}
		document.querySelector('#screenWidth').innerHTML = 'width: ' + window.innerWidth + 'px';
	};

	// 웹 환경에서의 메뉴 기능 제어
	const setWebMenu = () => {
		menuButton.style.display = 'none'; // 웹에서는 메뉴 버튼 숨김
		closeButton.style.display = 'none'; // 닫기 버튼 숨김
		mainMenu.classList.remove('open');  // 메뉴 열림 상태 초기화

		// 서브 메뉴 호버 이벤트로 변환
		menuItems.forEach(menuItem => {
			addHoverEvent(menuItem);
			removeClickEvent(menuItem);
		});
	};

	// 모바일 환경에서의 메뉴 기능 제어
	const setMobileMenu = () => {
		menuButton.style.display = 'block'; // 모바일 환경에서는 메뉴 버튼 표시
		closeButton.style.display = 'none'; // 닫기 버튼 숨김

		// 서브 메뉴 클릭 이벤트로 변환
		menuItems.forEach(menuItem => {
			addClickEvent(menuItem);
			removeHoverEvent(menuItem);
		});
	};

	/**
	 * 서브 메뉴 클릭 이벤트 추가 함수
	 * @see setMobileMenu
	 */
	const addClickEvent = (menuItem) => {
		const menuLink = menuItem.querySelector('.menu-title');
		menuLink.addEventListener('click', handleClick);
	};

	/** 
	 * 서브 메뉴 클릭 이벤트 제거 함수 
	 * @see setWebMenu
	 */
	const removeClickEvent = (menuItem) => {
		const menuLink = menuItem.querySelector('.menu-title');
		menuLink.removeEventListener('click', handleClick);
	};

	/** 
	 * 서브 메뉴 호버 이벤트 추가 함수
	 * @see setWebMenu
	*/
	const addHoverEvent = (menuItem) => {
		menuItem.addEventListener('mouseenter', handleHoverIn);
		menuItem.addEventListener('mouseleave', handleHoverOut);
	};

	/**
	 * 서브 메뉴 호버 이벤트 제거 함수
	 * @see setMobileMenu
	 */
	const removeHoverEvent = (menuItem) => {
		menuItem.removeEventListener('mouseenter', handleHoverIn);
		menuItem.removeEventListener('mouseleave', handleHoverOut);
	};

	/**
	 * 클릭 핸들러 함수
	 * @see addClickEvent
	 * @see removeClickEvent
	 */
	const handleClick = (e) => {
		e.preventDefault(); // 기본 링크 동작 방지
		e.target.closest('.has-submenu').classList.toggle('open'); // 서브 메뉴 열고 닫기
	};

	/** 
	 * 호버 인 핸들러
	 * @see addHoverEvent
	 * @see removeHoverEvent */
	const handleHoverIn = (e) => {
		e.target.closest('.has-submenu').classList.add('open');
	};

	/** 
	 * 호버 아웃 핸들러
	 * @see addHoverEvent
	 * @see removeHoverEvent */
	const handleHoverOut = (e) => {
		e.target.closest('.has-submenu').classList.remove('open');
	};

	// 메뉴 닫기 함수
	const closeMenu = () => {
		mainMenu.classList.remove('open'); // 메뉴 닫기
		menuButton.style.display = 'block'; // 메뉴 닫으면 햄버거 버튼 다시 보이기
		closeButton.style.display = 'none'; // 닫기 버튼 숨기기
	};

	// 닫기 버튼 추가 함수
	const addCloseButton = () => {
		if (!closeButton) {
			closeButton = document.createElement('span');
			closeButton.classList.add('menu-close');
			closeButton.innerHTML = '&times;';
			document.body.appendChild(closeButton);

			// 닫기 버튼 클릭 시 메뉴 닫기
			closeButton.addEventListener('click', closeMenu);
		}
	};

	// 메뉴 버튼 클릭 시 메뉴 열기/닫기
	const menuButtonClickEvent = () => {
		menuButton.addEventListener('click', () => {
			mainMenu.classList.toggle('open');
			if (mainMenu.classList.contains('open')) {
				menuButton.style.display = 'none'; // 메뉴가 열리면 햄버거 버튼 숨김
				closeButton.style.display = 'block'; // 닫기 버튼 표시
			} else {
				closeMenu(); // 메뉴 닫기
			}
		});
	}

	// 초기화 실행
	addCloseButton();  // 닫기 버튼 추가
	initMenuState();   // 화면 크기에 따른 초기 메뉴 상태 설정
	menuButtonClickEvent(); // 메뉴 버튼 클릭 이벤트 추가

	// 리사이즈 이벤트 처리
	window.addEventListener('resize', initMenuState);
}

function ghkrdls() {

}