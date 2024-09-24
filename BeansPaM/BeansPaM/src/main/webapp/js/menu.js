// 메뉴에 대한 css 추가
let link = document.createElement('link');
link.rel = 'stylesheet';
link.href = '/BeansPaM/css/menu.css';
document.head.appendChild(link);

// 메뉴가 들어갈 header 체크 후 없다면 추가
if (document.getElementsByTagName('header').length == 0) {
	let header = document.createElement('header');
	document.body.insertAdjacentElement('afterbegin', header);
}

// jsp 추가
fetch('/BeansPaM/pages/menu.jsp')
	.then(response => {
		if (!response.ok) {
			throw new Error('네트워크 응답에 문제가 있습니다.');
		}
		return response.text();
	})
	.then(data => {
		document.getElementsByTagName('header')[0].innerHTML = data;
		menu_js();
	})
	.catch(error => {
		console.error('fms_header_menu.html을 불러오는 중에 문제가 발생했습니다:', error);
	});

// 메뉴 js 추가
function menu_js() {
	
}