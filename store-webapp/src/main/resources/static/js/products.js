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
           addProductToCart(product);
        }
    }))
});