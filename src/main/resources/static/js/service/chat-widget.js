(function () {
  const fab = document.getElementById("ai-chat-fab");
  const panel = document.getElementById("ai-chat-panel");
  const closeBtn = document.getElementById("ai-chat-close");
  const form = document.getElementById("ai-chat-form");
  const input = document.getElementById("ai-chat-input");
  const sendBtn = document.getElementById("ai-chat-send");
  const messages = document.getElementById("ai-chat-messages");

  if (!fab || !panel || !form || !input || !sendBtn || !messages) {
    return;
  }

  function addMessage(text, role) {
    const row = document.createElement("div");
    row.className = "ai-chat-row " + role;
    const bubble = document.createElement("div");
    bubble.className = "ai-chat-bubble";
    bubble.textContent = text;
    row.appendChild(bubble);
    messages.appendChild(row);
    messages.scrollTop = messages.scrollHeight;
  }

  function setLoading(isLoading) {
    sendBtn.disabled = isLoading;
    sendBtn.textContent = isLoading ? "..." : "Gửi";
  }

  fab.addEventListener("click", function () {
    panel.classList.toggle("is-open");
    if (panel.classList.contains("is-open")) {
      input.focus();
    }
  });

  if (closeBtn) {
    closeBtn.addEventListener("click", function () {
      panel.classList.remove("is-open");
    });
  }

  form.addEventListener("submit", async function (event) {
    event.preventDefault();

    const message = input.value.trim();
    if (!message) {
      return;
    }

    addMessage(message, "user");
    input.value = "";
    setLoading(true);

    try {
      const response = await fetch("/api/chat/send", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ message: message })
      });

      const data = await response.json();
      const reply = data && data.result ? data.result : (data && data.message ? data.message : "Hiện chưa có phản hồi.");
      addMessage(reply, "bot");
    } catch (error) {
      addMessage("Dịch vụ AI đang bận, bạn thử lại sau nhé.", "bot");
    } finally {
      setLoading(false);
      input.focus();
    }
  });
})();