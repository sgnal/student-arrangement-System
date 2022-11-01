(function (window,document,$){
    function Game2048(opt){
        var prefix = opt.prefix,len=opt.len,size=opt.size,margin=opt.margin;
        var view = new View(prefix,len,size,margin);
        view.init();

    }
    window['Game2048']=Game2048;
})(window,document,jQuery);

function View(prefix,len,size,margin){
    this.prefix = prefix;
    this.len = len;
    this.size = size;
    this.margin = margin;
    this.container = $('#'+prefix+'_container');
    var containerSize = len* size +margin*(len+1);
    this.container.css({width:containerSize,height:containerSize});
    this.nums = {};
    View.prototype={
        getPos:function (n){
            return this.margin+n*(this.size+this.margin);
        },
        init:function (){
            for (var x = 0,len=this.len;x<len; ++x) {
                for (var y = 0; y < len; ++y) {
                    var $cell = $('<div class="'+this.prefix+'-cell"></div>');
                    $cell.css({
                        width:this.size+'px',height: this.size+'px',
                        top:this.getPos(x),left:this.getPos(y)

                    }).appendTo(this.container);


                }

            }
        }
    }

}

