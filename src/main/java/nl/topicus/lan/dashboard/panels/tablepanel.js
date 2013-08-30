(function($) {
	
	$.widget("ui.tablePanel", {	
		
		options : {
			xsize: 20,
			ysize: 20,
			tableMap : {"1,1" : "testTableName", "1,2" : "testTableName2" },
			tableNames : {},
			tableGames : {},
			tablePlayers : {}
		},
		
		_create : function() {
			var self, x, y, table;
			
			self = this;
			
			for(y = 0; y < self.options.ysize; y++){
				for(x = 0; x < self.options.xsize; x++){
					table = self.options.tableMap[x + "," + y];
					if(table){
						self.addTable(table.name);
					} else {
						self.addLegeRuimte();
					}
				}				
			}
		}, 
		
		addLegeRuimte : function(){
			var self;
			self = this;
			emptyTableDiv = document.createElement("div");
			emptyTableDiv.style="height:" + self.calculateYSize() + "px; width:" + self.calculateXSize() + "px; float: left; border: 1px solid black;";
			self.getMainDiv().append(emptyTableDiv);
		},
		
		addTable : function(id){
			var table;
			self = this;
			table = document.createElement("div");
			table.style="height:" + self.calculateYSize() + "px; width:" + self.calculateXSize() + "px; background-color: red; float: left; border: 1px solid black;";
			table.id = id;
			self.getMainDiv().append(table);			
		},
		
		calculateXSize : function(){
			var self, width;
			self = this;
			width = self.getMainDiv().width();
			width = width - (self.options.xsize * 2);
			return width / self.options.xsize;
		},
		
		calculateYSize : function(){
			var self, height;
			self = this;
			height = self.getMainDiv().height();
			height = height - (self.options.ysize * 2);
			return height / self.options.ysize;
		},

		getMainDiv : function(){
			return $(".tables");
		},
	
	});
}(jQuery));	
