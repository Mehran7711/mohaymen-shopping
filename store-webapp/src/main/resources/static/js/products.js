document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (pageNo) => ({
        pageNo: pageNo,
        products: {
            data: []
        },
        rateLimited: false,
        init() {
            this.loadProducts(this.pageNo);
            updateCartItemCount();
        },
        loadProducts(pageNo) {
            this.rateLimited = false;
            $.ajax({
                url: "/api/products?page=" + pageNo,
                success: (resp) => {
                    this.products = resp;
                },
                error: (xhr) => {
                    if (xhr.status === 401) {
                        alert("You should login.");
                    }
                    if (xhr.status === 429) {
                        this.rateLimited = true;
                        this.products = { data: [] };
                    }
                }
            });
        },
        addToCart(product) {
            if (!(product.quantity > 0)) {
                return;
            }
            addProductToCart(product);
        },
        increaseStock(product) {
            const amount = Number(product.stockAdd || 1);
            if (!(amount > 0)) {
                product.stockMessage = "Enter a quantity greater than zero";
                return;
            }
            product.stockMessage = "";
            $.ajax({
                url: "/api/products/" + product.id + "/stock",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify({ quantity: amount }),
                success: (available) => {
                    product.quantity = Number(available);
                    product.stockAdd = "";
                    product.stockMessage = "Stock is now " + product.quantity;
                },
                error: (xhr) => {
                    if (xhr.status === 401) {
                        product.stockMessage = "You should login.";
                        return;
                    }
                    product.stockMessage = "Could not add stock";
                }
            });
        }
    }))
});