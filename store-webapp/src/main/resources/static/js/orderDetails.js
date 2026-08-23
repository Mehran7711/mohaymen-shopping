document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (orderNumber) => ({
        orderNumber: orderNumber,
        orderDetails: {
            id: {},
            status: {},
            orderNumber: {},
            totalAmount: {},
            items: [],
            customer: {},
            deliveryAddress: {}
        },
        init() {
            this.getOrderDetails(this.orderNumber)
        },
        getOrderDetails(orderNumber) {
            console.log("Entering to order_detail: ", orderNumber)
            $.getJSON("/order/get/" + orderNumber, (data) => {
                this.orderDetails = data
                console.log("Get Order Resp: ", data)
            });
        }
    }))
});
