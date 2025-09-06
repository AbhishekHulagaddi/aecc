



window.viewPDF = function(path) {
  console.log("Loading PDF:", path);
  document.getElementById("notes-list").style.display = "none";
  document.getElementById("pdf-viewer-container").style.display = "block";

  const viewerUrl = `/pdfjs/web/viewer.html?file=${encodeURIComponent(path)}`;
  document.getElementById("pdf-viewer-frame").src = viewerUrl;
};

window.closeViewer = function() {
  document.getElementById("pdf-viewer-container").style.display = "none";
  document.getElementById("notes-list").style.display = "flex";
  document.getElementById("pdf-viewer-frame").src = "";
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

    // Clear all storage
    sessionStorage.clear();
    localStorage.clear();

    // Redirect to login page
    window.location.href = "index.html";


}
