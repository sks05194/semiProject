
const originalEmployees = [
	// { employee: '33334444', name: '강동준', department: '기획부', position: '사원', email: 's@coffee.com', tel: '02-3333-4444' },
	// { employee: '44443333', name: '강동준', department: '관리부', position: '대리', email: 't@coffee.com', tel: '02-4444-3333' },
	// { employee: '11111111', name: '민기홍', department: '영업부', position: '부장', email: 'a@coffee.com', tel: '02-1111-1111' },
	// { employee: '22222222', name: '김둘리', department: '기획부', position: '사원', email: 'b@coffee.com', tel: '02-2222-2222' },
	// { employee: '33333333', name: '김또치', department: '기획부', position: '과장', email: 'c@coffee.com', tel: '02-3333-3333' },
	// { employee: '44444444', name: '김영희', department: '관리부', position: '대리', email: 'd@coffee.com', tel: '02-4444-4444' },
	// { employee: '55555555', name: '김철수', department: '관리부', position: '인턴', email: 'e@coffee.com', tel: '02-5555-5555' },
	// { employee: '66666666', name: '김희동', department: '기획부', position: '사원', email: 'f@coffee.com', tel: '02-6666-6666' },
	// { employee: '55556666', name: '나미리', department: '관리부', position: '인턴', email: 'u@coffee.com', tel: '02-5555-6666' },
	// { employee: '90909090', name: '매앵구', department: '관리부', position: '대리', email: 'p@coffee.com', tel: '02-9090-9090' },
	// { employee: '99999999', name: '박길동', department: '기획부', position: '사원', email: 'g@coffee.com', tel: '02-9999-9999' },
	// { employee: '12121212', name: '박희동', department: '영업부', position: '인턴', email: 'h@coffee.com', tel: '02-1212-1212' },
	// { employee: '78787878', name: '송상훈', department: '영업부', position: '차장', email: 'n@coffee.com', tel: '02-7878-7878' },
	// { employee: '11221122', name: '김보라', department: '관리부', position: '대리', email: 'k@coffee.com', tel: '02-1122-1122' },
	// { employee: '33443344', name: '신짱아', department: '기획부', position: '사원', email: 'l@coffee.com', tel: '02-3344-3344' },
	// { employee: '55665566', name: '신형만', department: '관리부', position: '대리', email: 'm@coffee.com', tel: '02-5566-5566' },
	// { employee: '34343434', name: '이길동', department: '기획부', position: '과장', email: 'i@coffee.com', tel: '02-3434-3434' },
	// { employee: '99009900', name: '이훈이', department: '관리부', position: '부장', email: 'q@coffee.com', tel: '02-9900-9900' },
	// { employee: '56565656', name: '이희동', department: '기획부', position: '차장', email: 'j@coffee.com', tel: '02-5656-5656' },
	// { employee: '77778888', name: '차은주', department: '기획부', position: '사원', email: 'w@coffee.com', tel: '02-7777-8888' },
	// { employee: '66665555', name: '채성아', department: '관리부', position: '인턴', email: 'v@coffee.com', tel: '02-6666-5555' },
	// { employee: '88887777', name: '치이타', department: '관리부', position: '대리', email: 'x@coffee.com', tel: '02-8888-7777' },
	// { employee: '11112222', name: '한수지', department: '영업부', position: '사원', email: 'q@coffee.com', tel: '02-1111-2222' },
	// { employee: '22221111', name: '한유리', department: '관리부', position: '대리', email: 'r@coffee.com', tel: '02-2222-1111' },
	// { employee: '77887788', name: '흰둥이', department: '영업부', position: '인턴', email: 'o@coffee.com', tel: '02-7788-7788' }
	
];

let filteredEmployees = [...originalEmployees];
let currentPage = 1;
const rowsPerPage = 10;

function displayTable(page) {
	const tableBody = document.querySelector('#employeeTable tbody');
	const pagination = document.getElementById('pagination');
	tableBody.innerHTML = '';
	pagination.innerHTML = '';

	const start = (page - 1) * rowsPerPage;
	const end = start + rowsPerPage;
	const paginatedItems = filteredEmployees.slice(start, end);

	paginatedItems.forEach(employee => {
		const row = document.createElement('tr');
		row.innerHTML = `
			<td>${employee.employee}</td>
			<td>${employee.name}</td>
			<td>${employee.department}</td>
			<td>${employee.position}</td>
			<td>${employee.email}</td>
			<td>${employee.tel}</td>
		`;
		tableBody.appendChild(row);
	});

	const totalPages = Math.ceil(filteredEmployees.length / rowsPerPage);

	for (let i = 1; i <= totalPages; i++) {
		const button = document.createElement('button');
		button.innerText = i;
		button.className = i === page ? 'active' : '';
		button.addEventListener('click', () => {
			currentPage = i;
			displayTable(i);
		});
		pagination.appendChild(button);
		pagination.appendChild(document.createTextNode(' '));
	}
}

function filterTable() {
	const searchField = document.getElementById('searchField').value;
	const searchInput = document.getElementById('searchInput').value.toLowerCase();
	filteredEmployees = originalEmployees.filter(employee => {
		return employee[searchField].toLowerCase().includes(searchInput);
	});
	currentPage = 1;
	displayTable(currentPage);
	document.getElementById('noResults').style.display = filteredEmployees.length === 0 ? 'block' : 'none';
}

displayTable(currentPage);