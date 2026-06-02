const employeeModal = document.getElementById('employeeModal');
const confirmModal = document.getElementById('confirmModal');
const modalTitle = document.getElementById('modalTitle');
const employeeIdField = document.getElementById('employeeIdField');


const STORAGE_KEY = 'hr_manager_employees';


function showModal(id) {
    document.getElementById(id).style.display = 'block';
}

function closeModal(id) {
    document.getElementById(id).style.display = 'none';
}


function setupAddModal() {
    modalTitle.textContent = 'Agregar Empleado';
    document.getElementById("employeeIdField").value = "";
    document.getElementById("names").value = "";
    document.getElementById("lastName").value = "";
    document.getElementById("dni").value = "";
    document.getElementById("email").value = "";
    document.getElementById("position").value = "";
    document.getElementById("salary").value = "";
    showModal('employeeModal');
}


document.getElementById('btnAddEmployee').addEventListener('click', setupAddModal);


window.onclick = function (event) {
    if (event.target == employeeModal) {
        closeModal('employeeModal');
    }
    if (event.target == confirmModal) {
        closeModal('confirmModal');
    }
}

document.querySelectorAll(".btn-edit").forEach(button => {
    button.addEventListener("click", function () {
        const employeeId = this.getAttribute("data-id");

        fetch(`/employees/employee/${employeeId}`)
            .then(response => response.json())
            .then(data => {
                modalTitle.textContent = 'Editar Empleado';
                showModal('employeeModal');


                document.getElementById("employeeIdField").value = data.idEmployee;
                document.getElementById("names").value = data.names;
                document.getElementById("lastName").value = data.lastName;
                document.getElementById("dni").value = data.dni;
                document.getElementById("email").value = data.email;
                document.getElementById("position").value = data.position;
                document.getElementById("salary").value = data.salary;
                document.getElementById("status").value = data.status;
                document.getElementById("employeeDept").value = data.department.idDepartment;
            })
            .catch(err => console.error("Error al cargar empleado:", err));
    });
});

