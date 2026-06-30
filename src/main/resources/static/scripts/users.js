const userModal = document.getElementById('userModal');
const modalTitle = document.getElementById('modalTitle');
const userIdField = document.getElementById('userIdField');

function showModal(id) {
    document.getElementById(id).style.display = 'block';
}

function closeModal(id) {
    document.getElementById(id).style.display = 'none';
}

function setupAddModal() {
    modalTitle.textContent = 'Agregar Usuario';
    userIdField.value = "";
    document.getElementById("userEmail").value = "";
    document.getElementById("userPassword").value = "";
    document.getElementById("userPassword").required = true;
    document.getElementById("userPassword").placeholder = "Contrase&ntilde;a del usuario";
    document.getElementById("userRol").value = "USUARIO";
    document.getElementById("userStatus").value = "true";
    document.getElementById("userEmployee").value = "";
    showModal('userModal');
}

document.getElementById('btnAddUser').addEventListener('click', setupAddModal);

window.onclick = function (event) {
    if (event.target == userModal) {
        closeModal('userModal');
    }
}

document.querySelectorAll(".btn-edit").forEach(button => {
    button.addEventListener("click", function () {
        const userId = this.getAttribute("data-id");

        fetch(`/users/user/${userId}`)
            .then(response => response.json())
            .then(data => {
                modalTitle.textContent = 'Editar Usuario';
                userIdField.value = data.idUser;
                document.getElementById("userEmail").value = data.email;
                document.getElementById("userPassword").value = "";
                document.getElementById("userPassword").required = false;
                document.getElementById("userPassword").placeholder = "Dejar vac&iacute;o para mantener";
                document.getElementById("userRol").value = data.rol;
                document.getElementById("userStatus").value = data.estatus ? "true" : "false";
                document.getElementById("userEmployee").value = data.employee ? data.employee.idEmployee : "";
                showModal('userModal');
            })
            .catch(err => console.error("Error al cargar usuario:", err));
    });
});
