document.addEventListener('alpine:init', () => {
    Alpine.data('initData', () => ({
        cart: {items: [], totalAmount: 0},
        orderError: "",
        orderForm: {
            customer: {
                name: "",
                email: "",
                phone: ""
            },
            deliveryAddress: {
                addressLine1: "",
                addressLine2: "",
                city: "",
                state: "",
                zipCode: "",
                country: ""
            }
        },

        async init() {
            await this.loadCurrentUser();
            await this.loadCart();
            await updateCartItemCount();
        },
        async loadCart() {
            this.cart = await getCart();
        },
        async loadCurrentUser() {
            try {
                const response = await fetch("/user/current-user");

                if (!response.ok) {
                    throw new Error(`HTTP ${response.status}`);
                }

                const user = await response.json();

                console.log("user: " + user.phone);
                this.orderForm.customer = {
                    name: user.firstName + ' ' + user.lastName,
                    email: user.email,
                    phone: user.phone
                };

            } catch (error) {
                console.error("Cannot load current user:", error);
            }
        },
        async updateItemQuantity(id, quantity) {
            try {
                this.cart = await updateProductQuantity(id, quantity);
                console.log("updateProductQuantity successful!");
                await updateCartItemCount();
            } catch (error) {
                console.error("Update quantity failed:", error);
            }
        },
        removeCart() {
            deleteCart();
        },
        async createOrder() {
            this.orderError = "";
            try {
                const response = await fetch(
                    "/order/checkout?scenario=PAYMENT_SUCCESS",
                    {
                        method: "POST"
                    }
                );
                if (!response.ok) {
                    this.orderError = await readOrderError(response);
                    return;
                }
                const order = await response.json();
                console.log("Order created:", order);
                window.location = "/order/load/" + order.orderNumber

            } catch (error) {
                console.error("Order creation failed:", error);
                this.orderError = "Order creation failed";
            }
        },
    }))
});

async function readOrderError(response) {
    try {
        const body = await response.json();
        if (body && body.message) {
            return body.message;
        }
    } catch (error) {
        console.error("Could not read the order error", error);
    }
    return "Order creation failed";
}
