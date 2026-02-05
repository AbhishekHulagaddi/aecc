

window.onload = async function () {
    const token = sessionStorage.getItem("token");
    if (!token) {
        window.location.href = "index.html"; // Redirect to login
        return;
    }


    const userData = JSON.parse(sessionStorage.getItem("userData"));
    if (!userData) {
        window.location.href = "index.html";
        return;
    }

    await loadAdminDashboard();
    try {
        const response = await fetch(window.BASE_URL + "/Auth/User/FindByUserCode", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${userData.token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ usercode: userData.usercode })
        });

        if (!response.ok) throw new Error("Failed to fetch user status");

        const statusData = await response.json();

        // Set input values from API
        document.getElementById("register-userCode").value = statusData.usercode || '';
        document.getElementById("register-firstname").value = statusData.firstName || '';
        document.getElementById("register-lastname").value = statusData.lastName || '';
        document.getElementById("register-address").value = statusData.address || '';
        document.getElementById("register-email").value = statusData.mail || '';
        document.getElementById("register-mobile").value = statusData.mobileNumber || '';

    } catch (error) {
        document.getElementById("user-info").innerText = "Error fetching user details.";
        console.error(error);
    }
};

window.editUser = async function () {
   
    const token = sessionStorage.getItem("token");

    const userData = JSON.parse(sessionStorage.getItem("userData")); // ✅ Add this line

    if (!userData) {
        alert("User session expired. Please login again.");
        window.location.href = "index.html";
        return;
    }


    const updatedData = {
        userId: userData.userId,
        usercode: userData.usercode,
        firstName: document.getElementById("register-firstname").value,
        lastName: document.getElementById("register-lastname").value,
        address: document.getElementById("register-address").value,
        mail: userData.mail,
        mobileNumber: document.getElementById("register-mobile").value
    };

    
    try {
        const response = await fetch(window.BASE_URL + "/Auth/User/Update", {
            method: "POST", 
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedData)
        });

        if (!response.ok) {
            throw new Error("Failed to update user details");
        }

        const statusData = await response.json();



        alert(statusData.message);

    } catch (error) {
        alert("Error updating user details.");
        console.error(error);
    }
};

window.logout = async function () {
    alert("User Logged Out Successfully!!");
    const userData = JSON.parse(sessionStorage.getItem("userData"));
    const token = userData ? userData.token : null;

    try {
        if (token) {

            await fetch(window.BASE_URL + "/Auth/User/signout", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
        }
    } catch (error) {
        console.error("Error calling signout API:", error);
    }

    sessionStorage.clear();
    localStorage.clear();
    window.location.href = "index.html";
}




async function loadAdminDashboard() {

    try {
        // Here you will later call your real API
        // Example: const response = await fetch(window.BASE_URL + "/Admin/Dashboard", { headers: { Authorization: `Bearer ${token}` } });

        // Dummy Data for now 👇
        const admissions = { months: ["Jan", "Feb", "Mar", "Apr", "May"], data: [10, 25, 40, 35, 50] };
        const batchStrength = { batches: ["Batch A", "Batch B", "Batch C", "Batch D"], data: [45, 38, 50, 42] };
        const subjectPerformance = { subjects: ["Math", "Physics", "Chemistry", "Biology"], data: [78, 85, 72, 90] };
        const feeCollection = { labels: ["Collected", "Pending"], data: [750000, 250000] };
        const attendance = { months: ["Jan", "Feb", "Mar", "Apr", "May"], data: [88, 91, 85, 89, 93] };

        // Create charts
        createLineChart("admissionsChart", "Monthly Admissions", admissions.months, admissions.data, "rgba(54,162,235,0.7)");
        createBarChart("batchStrengthChart", "Batch Strength", batchStrength.batches, batchStrength.data, "rgba(75,192,192,0.7)");
        createBarChart("subjectPerformanceChart", "Average Subject Performance (%)", subjectPerformance.subjects, subjectPerformance.data, "rgba(255,159,64,0.7)", 100);
        createPieChart("feeCollectionChart", "Fee Collection Status", feeCollection.labels, feeCollection.data);
        createLineChart("attendanceChart", "Monthly Attendance (%)", attendance.months, attendance.data, "rgba(153,102,255,0.7)", 100);
    } catch (err) {
        console.error("Error loading admin dashboard:", err);
    }
}

function createLineChart(id, label, labels, data, color, maxY=undefined) {
    const ctx = document.getElementById(id).getContext("2d");
    return new Chart(ctx, {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                label: label,
                data: data,
                borderColor: color,
                backgroundColor: color.replace('0.7','0.2'),
                tension: 0.3,
                fill: true,
                datalabels: { align: 'top', anchor: 'end' }
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: { y: { beginAtZero: true, max: maxY } },
            plugins: { legend: { position: "top" }, datalabels: { color: "#000" } }
        },
        plugins: [ChartDataLabels]
    });
}

function createBarChart(id, label, labels, data, color, maxY=undefined) {
    const ctx = document.getElementById(id).getContext("2d");
    return new Chart(ctx, {
        type: "bar",
        data: {
            labels: labels,
            datasets: [{
                label: label,
                data: data,
                backgroundColor: color,
                borderRadius: 6,
                datalabels: { align: 'end', anchor: 'end' }
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: { y: { beginAtZero: true, max: maxY } },
            plugins: { legend: { position: "top" }, datalabels: { color: "#000" } }
        },
        plugins: [ChartDataLabels]
    });
}

function createPieChart(id, title, labels, data) {
    const ctx = document.getElementById(id).getContext("2d");
    return new Chart(ctx, {
        type: "pie",
        data: { labels: labels, datasets: [{ data: data, backgroundColor: ["#4CAF50","#FF5252"] }] },
        options: { plugins: { legend: { position: "bottom" }, title: { display: true, text: title } } }
    });
}


