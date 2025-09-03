function toggleChatbox() {
  const chatbox = document.getElementById("chatbox");
  chatbox.style.display = (chatbox.style.display === "flex") ? "none" : "flex";
}

function sendMessage() {
  const input = document.getElementById("chatInput");
  const msg = input.value.trim();
  if (!msg) return;

  const messagesDiv = document.getElementById("messages");

  // Display user message
  const userMsg = document.createElement("p");
  userMsg.innerHTML = `<strong>You:</strong> ${msg}`;
  messagesDiv.appendChild(userMsg);
  input.value = "";
  messagesDiv.scrollTop = messagesDiv.scrollHeight;

  // Call API for bot response
  fetch("http://192.168.2.28:8180/ChatBot/ChatBot/Chat", {
    method: "POST",
    headers: { "Content-Type": "text/plain" },
    body: msg
  })
  .then(res => res.text())
  .then(data => {
    const botReply = document.createElement("p");
    botReply.innerHTML = `<strong>Bot:</strong> ${data}`;
    messagesDiv.appendChild(botReply);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
  })
  .catch(err => {
    const errorMsg = document.createElement("p");
    errorMsg.innerHTML = `<strong>Bot:</strong> Error: ${err}`;
    messagesDiv.appendChild(errorMsg);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
  });
}

// Send message with Enter key
document.getElementById("chatInput").addEventListener("keydown", function(event) {
  if (event.key === "Enter") {
    event.preventDefault();
    sendMessage();
  }
});