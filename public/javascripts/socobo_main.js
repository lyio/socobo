Polymer({
    data: [],
    fontSize: 24,
    add: function () {
        if (this.newNote) {
            this.data.unshift({
                body: this.newNote,
                done: false
            })
        }
        this.$.newNoteInput.style.display = 'none';
        this.$.newNoteInput.value = null;
    },
    toggleDrawer: function () {
        this.$.drawerPanel.togglePanel();
    },
    ready: function () {
        this.$.newNoteInput.style.display = 'none';
    },
    showNewNoteInput: function () {
        this.$.newNoteInput.style.display = 'block';
    },
    dataChanged: function () {
        this.$.storage.save();
    },

    delete: function (e) {
        this.data = this.data.filter(function (item) {
            return !item.done;
        })
    },

    fontSizeChanged: function () {
        var cards = this.shadowRoot.querySelectorAll('.card');
        for (var i = 0; i < cards.length; i++) {
            cards[i].style.fontSize = this.fontSize + 'px'
        }
    },

    reset: function () {
        this.fontSize = 14;
        this.fadeSelected = false;
        this.$.toast.show();
    },

    showSuccessToast: function() {
        this.toast.show();
    },

    saveFridge: function () {
        this.saveitem = {};
        this.saveitem.amount = 1;
        this.saveitem.unit = "PIECE";
        this.saveitem.produce = {name: this.data[0].body};
        this.$.ajax.body = JSON.stringify(this.saveitem);
        this.$.ajax.contentType = 'application/json';
        this.$.ajax.go();
    }
});
