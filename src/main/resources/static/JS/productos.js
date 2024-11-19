document.addEventListener("DOMContentLoaded", () => {
    const stockCells = document.querySelectorAll(".stock");

    stockCells.forEach(cell => {
        const stock = parseInt(cell.textContent.trim(), 10);

        if (stock <= 2) {
            const productName = cell.parentElement.querySelector("td:first-child").textContent.trim();
            alert(`Advertencia: Quedan pocas unidades del producto "${productName}". Stock actual: ${stock}`);
        }
    });
});