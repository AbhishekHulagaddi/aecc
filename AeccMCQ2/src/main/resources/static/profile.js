//const API_URL = "http://localhost:8081/Auth";
const API_URL = "https://tierraagniveshacoaching.up.railway.app/Auth";

window.onload = async function () {
    const token = sessionStorage.getItem("token");
    const mcqData = sessionStorage.getItem("mcqData")
    if (!token) {
        window.location.href = "index.html"; // Redirect to login
        return;
    }

    if(mcqData){
        sessionStorage.removeItem("mcqData");
    }

    const userData = JSON.parse(sessionStorage.getItem("userData"));
    if (!userData) {
        window.location.href = "index.html";
        return;
    }

    await loadDashboardCharts();

    try {
        const response = await fetch(`${API_URL}/User/FindByUserCode`, {
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
        const response = await fetch(`${API_URL}/User/Update`, {
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
   //         await fetch("http://localhost:8081/Auth/User/signout", {
            await fetch("https://tierraagniveshacoaching.up.railway.app/Auth/User/signout", {
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

async function loadDashboardCharts() {
    const token = sessionStorage.getItem("token");

    if (!token) {
        window.location.href = "index.html"; // Redirect to login
        return;
    }

    try {
 //       const response = await fetch("http://localhost:8081/Result/Result/UserDashboard", {
        const response = await fetch("https://tierraagniveshacoaching.up.railway.app/Result/Result/UserDashboard", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            body: "{}" // explicitly send empty JSON body
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();

        // Split data into top and bottom halves
        const firstHalf = data.slice(0, 7);
        const secondHalf = data.slice(7, 14);

        // Prepare datasets for top chart
        const labelsTop = firstHalf.map(item => item.subjectName);
        const availableTop = firstHalf.map(item => item.availableTest);
        const coveredTop = firstHalf.map(item => item.attendedTest);

        // Prepare datasets for bottom chart
        const labelsBottom = secondHalf.map(item => item.subjectName);
        const availableBottom = secondHalf.map(item => item.availableTest);
        const coveredBottom = secondHalf.map(item => item.attendedTest);

        // Create both charts
        createChart('testStatusChartTop', labelsTop, availableTop, coveredTop);
        createChart('testStatusChartBottom', labelsBottom, availableBottom, coveredBottom);

    } catch (error) {
        console.error("Error loading dashboard data:", error);
    }
}

function createChart(ctxId, labels, available, covered) {
    const ctx = document.getElementById(ctxId).getContext('2d');
    return new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Tests Available',
                    data: available,
                    backgroundColor: 'rgba(0, 143, 21, 0.7)',
                    barThickness: 50,
                    categoryPercentage: 0.6,
                    barPercentage: 0.5,
                    datalabels: {
                        color: '#000',
                        anchor: 'end',
                        align: 'top',
                        font: {
                            weight: 'bold',
                            size: 12
                        }
                    }
                },
                {
                    label: 'Tests Covered',
                    data: covered,
                    backgroundColor: 'rgba(159, 230, 131, 0.7)',
                    barThickness: 50,
                    categoryPercentage: 0.6,
                    barPercentage: 0.5,
                    datalabels: {
                        color: '#000',
                        anchor: 'end',
                        align: 'top',
                        font: {
                            weight: 'bold',
                            size: 12
                        }
                    }
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'top'
                },
                datalabels: {
                    display: true
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    max: 25,
                    title: {
                        display: true,
                        text: 'Tests'
                    }
                }
            }
        },
        plugins: [ChartDataLabels] // ✅ required for showing text
    });
}


