document.addEventListener("DOMContentLoaded", () => {
    // Tải sidebar
    fetch('../HTML/sidebar.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('sidebar-placeholder').innerHTML = data;
        });

    // Biểu đồ doanh thu
    const revenueData = {
        labels: ['20/10', '21/10', '22/10', '23/10', '24/10', '25/10', '26/10'],
        datasets: [{
            label: 'Doanh thu (triệu VNĐ)',
            data: [12, 18, 14, 20, 25, 30, 40],
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 2,
        }]
    };

    const revenueChartConfig = {
        type: 'line',
        data: revenueData,
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'top' },
                title: { display: true, text: 'Biểu đồ doanh thu theo tuần' }
            }
        },
    };

    const revenueChartCtx = document.getElementById('revenueChart').getContext('2d');
    const revenueChart = new Chart(revenueChartCtx, revenueChartConfig);

    // Xử lý sắp xếp
    document.getElementById('chartTimeRange').addEventListener('change', function () {
        const range = this.value;
        let labels, data, titleText;

        if (range === '7days') {
            labels = ['20/10', '21/10', '22/10', '23/10', '24/10', '25/10', '26/10'];
            data = [12, 18, 14, 20, 25, 30, 40];
            titleText = 'Biểu đồ doanh thu theo tuần';
        } else if (range === 'month') {
            labels = ['Tuần 1', 'Tuần 2', 'Tuần 3', 'Tuần 4'];
            data = [50, 60, 55, 70];
            titleText = 'Biểu đồ doanh thu theo tháng';
        } else {
            labels = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4'];
            data = [200, 240, 220, 250];
            titleText = 'Biểu đồ doanh thu theo năm';
        }

        revenueChart.data.labels = labels;
        revenueChart.data.datasets[0].data = data;
        revenueChart.options.plugins.title.text = titleText;
        revenueChart.update();
    });
});
