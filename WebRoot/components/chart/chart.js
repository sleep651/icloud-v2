;(function($) {

	$.fn.chart = function(){
	
		var options = $.extend(options,arguments[0]);
		$(this).highcharts({
				chart: {
					backgroundColor:options.color,
					type: options.type
				},
				 title: {
                			text: options.title
            			},
				xAxis: {
					 categories: options.label
				},
				yAxis: {
					title:'',
					labels: {
						formatter: function() {
							return this.value;
						}
					}
				},
				legend: {
					layout: 'vertical',
					x: 0,
					align:'right',
					verticalAlign: 'top',
					y: -10,
					floating: true
				},
			series: options.json
        });
	}

})(jQuery);