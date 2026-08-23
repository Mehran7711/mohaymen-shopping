document.addEventListener('alpine:init', () => {
    Alpine.data('initData', () => ({
        orders: [],
        init() {
            this.loadOrders();
        },
        loadOrders() {
            $.getJSON("/order/all", (data) => {
                console.log("orders :", data)
                this.orders = data
            });
        },
    }))
});
