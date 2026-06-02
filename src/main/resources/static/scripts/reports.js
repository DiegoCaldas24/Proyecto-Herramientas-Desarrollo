async function getDataApi() {
    try {
        const response = await fetch("http://localhost:8080/employees/data");
        return await response.json();
    } catch (error) {
        console.error("Error al obtener datos:", error);
        return [];
    }
}


async function getEmployees() {
    return await getDataApi();
}


async function getReportData() {
    const employees = await getEmployees(); // Espera datos reales


    const deptCounts = employees.reduce((acc, employee) => {
        const deptName = employee.department?.name || "Sin departamento";
        acc[deptName] = (acc[deptName] || 0) + 1;
        return acc;
    }, {});


    const attendanceData = {
        present: 45,
        absent: 3,
        late: 2
    };

    return {
        totalEmployees: employees.length,
        deptLabels: Object.keys(deptCounts),
        deptData: Object.values(deptCounts),
        attendanceData
    };
}


function renderMetrics(data) {
    document.getElementById('totalEmployees').textContent = data.totalEmployees;
}

function renderDeptChart(labels, data) {
    const ctx = document.getElementById('deptChart').getContext('2d');

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels,
            datasets: [{
                label: 'Número de Empleados',
                data,
                backgroundColor: [
                    'rgba(30, 64, 175, 0.8)',
                    'rgba(5, 150, 105, 0.8)',
                    'rgba(245, 158, 11, 0.8)',
                    'rgba(239, 68, 68, 0.8)',
                    'rgba(99, 102, 241, 0.8)'
                ],
                borderWidth: 1,
                borderRadius: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {beginAtZero: true}
            }
        }
    });
}

function renderAttendanceChart(attendanceData) {
    const ctx = document.getElementById('attendanceChart').getContext('2d');

    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Presente', 'Ausente', 'Tarde'],
            datasets: [{
                data: [
                    attendanceData.present,
                    attendanceData.absent,
                    attendanceData.late
                ],
                backgroundColor: [
                    'var(--color-secondary)',
                    '#ef4444',
                    '#f59e0b'
                ],
                hoverOffset: 4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
}


window.onload = async function () {
    const reportData = await getReportData();

    renderMetrics(reportData);
    renderDeptChart(reportData.deptLabels, reportData.deptData);
    renderAttendanceChart(reportData.attendanceData);
};