const API_URL = "http://localhost:8081/Auth"; // Update if needed



window.logout = async function () {
    alert("User Logged Out Successfully!!");
    const userData = JSON.parse(sessionStorage.getItem("userData"));
    const token = userData ? userData.token : null;

    try {
        if (token) {
            await fetch(`${API_URL}User/signout`, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
        }
    } catch (error) {
        console.error("Error calling signout API:", error);
    }

    // Clear all storage
    sessionStorage.clear();
    localStorage.clear();

    // Redirect to login page
    window.location.href = "index.html";
}




