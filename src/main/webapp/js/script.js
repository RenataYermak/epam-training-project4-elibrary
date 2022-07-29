function preventBack() {
    window.history.forward();
}

setTimeout("preventBack()", 0);
window.onunload = function () {
    null
};

function show() {
    var a = document.getElementById("pwd");
    if (a.type === "password") {
        a.type = "text";
    } else {
        a.type = "password";
    }
};

function test() {
    var thetarget = document.getElementById("b");
    thetarget.innerHTML = document.getElementById("a").value;
    thetarget.scrollTop = thetarget.scrollHeight;
};

document.addEventListener('DOMContentLoaded', function () {
    const table = document.getElementById('sorted-table');
    const headers = table.querySelectorAll('th');
    const tableBody = table.querySelector('tbody');
    const rows = tableBody.querySelectorAll('tr');

    const directions = Array.from(headers).map(function (header) {
        return '';
    });

    const transform = function (index, content) {
        const type = headers[index].getAttribute('data-type');
        switch (type) {
            case 'date':
                return date(content);
            case 'string':
            default:
                return content;
        }
    };

    const sortColumn = function (index) {
        const direction = directions[index] || 'asc';
        const multiplier = (direction === 'asc') ? 1 : -1;
        const newRows = Array.from(rows);

        newRows.sort(function (rowA, rowB) {
            const cellA = rowA.querySelectorAll('td')[index].innerHTML;
            const cellB = rowB.querySelectorAll('td')[index].innerHTML;

            const a = transform(index, cellA);
            const b = transform(index, cellB);

            switch (true) {
                case a > b:
                    return 1 * multiplier;
                case a < b:
                    return -1 * multiplier;
                case a === b:
                    return 0;
            }
        });

        [].forEach.call(rows, function (row) {
            tableBody.removeChild(row);
        });

        directions[index] = direction === 'asc' ? 'desc' : 'asc';

        newRows.forEach(function (newRow) {
            tableBody.appendChild(newRow);
        });
    };

    [].forEach.call(headers, function (header, index) {
        header.addEventListener('click', function () {
            sortColumn(index);
        });
    });
});