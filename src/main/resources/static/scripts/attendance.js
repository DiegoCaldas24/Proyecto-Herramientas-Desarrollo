const clockDisplay = document.getElementById('clock');
const btnEntrada = document.getElementById('btnEntrada');
const btnSalida = document.getElementById('btnSalida');
const employeeInput = document.getElementById('employeeId');

let currentMode = 'entrada';


function updateClock() {
    const now = new Date();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    clockDisplay.textContent = `${hours}:${minutes}:${seconds}`;
}


updateClock();
setInterval(updateClock, 1000);

document.getElementById("employeeId").addEventListener("input", function () {
    if (this.value.length === 8) {
        document.getElementById("employeeForm").submit();
    }
});

window.onload = function () {
    document.getElementById("employeeId").focus()
}
