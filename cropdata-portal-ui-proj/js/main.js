// Blurry Image Load version 3.0.0
class BlurryImageLoad {
	supportsCSSFilters(t) {
		void 0 === t && (t = !1);
		const e = document.createElement('test');
		e.style.cssText = (t ? '-webkit-' : '') + 'filter: blur(2px)';
		const s = 0 != e.style.length,
			r = void 0 === document.documentMode || document.documentMode > 9;
		return s && r;
	}
	load(t = document.querySelectorAll('.blurry-load')) {
		if (!this.supportsCSSFilters(!0) && !this.supportsCSSFilters(!1)) for (let e of t) (e.src = ''), e.classList.add('no-blur'), e.classList.remove('blurry-load');
		for (let e of t) {
			const t = new Image()
				; (t.src = e.getAttribute('data-large')),
					(t.onload = () => {
						; (e.src = t.src), e.classList.add('blur-out'), e.classList.remove('blurry-load');
					});
		}
	}
}

if (window.location.host == 'dev.cropdata.in') {
	var baseUrl = 'https://api-dev.cropdata.tk/cropdata-portal/site';
} else if (window.location.host == 'uat.cropdata.in') {
	var baseUrl = 'https://api-uat.cropdata.tk/cropdata-portal/site';
} else if (window.location.host == 'www.cropdata.in' || window.location.host == 'cropdata.in') {
	var baseUrl = 'https://api-ts.cropdata.tk/cropdata-portal/site';
} else {
	// var baseUrl = "https://api-dev.cropdata.in/cropdata-portal/site";
	var baseUrl = 'https://api-dev.cropdata.tk/cropdata-portal/site';
}

if (window.location.host == 'dev.cropdata.in') {
	var aefcBaseUrl = 'https://api-dev.cropdata.in/aefc';
} else if (window.location.host == 'uat.cropdata.in') {
	var aefcBaseUrl = 'https://api-uat.cropdata.in/aefc';
} else if (window.location.host == 'www.cropdata.in' || window.location.host == 'cropdata.in') {
	var aefcBaseUrl = 'https://api-ts.cropdata.in/aefc';
} else {
	// var aefcBaseUrl = "https://api-ts.cropdata.in/aefc";
	var aefcBaseUrl = 'https://api-dev.cropdata.in/aefc';
}

// Check window width and set chart maxTicksLimit
var currentWindowWidth = $(window).width();
var maxTicksLimitSet;
if (currentWindowWidth > 1199) {
	// maxTicksLimitSet = 0;
} else {
	maxTicksLimitSet = 6;
}

// console.log(window.location.host);
// console.log(baseUrl);

var url = new URL(window.location.href);
var careerPaginationLength = 0;

$(document).ready(function () {
	var newsCount = 0;
	//Navigation Menu Active using url
	var menuActiveCheck = window.location.href; // because the 'href' property of the DOM element is the absolute path
	$('.nav-menu li a').each(function () {
		if (this.href === menuActiveCheck) {
			$(this).parent().addClass('active');
		}
	});
	$('.nav-menu li.submenu_parent a').each(function () {
		if (this.href === menuActiveCheck) {
			$(this).parent().parent().parent().addClass('active');
		}
	});

	//News See More Btn
	$('#seeMore').on('click', function (e) {
		e.preventDefault();
		$('.content:hidden').slice(0, 10).slideDown();
		if ($('.content:hidden').length == 0) {
			$(this).hide();
		}
	});

	// Cache selectors
	var lastId,
		topMenu = $('.activeMenu .nav-pills ul'),
		topMenuHeight = topMenu.outerHeight() + 180,
		// All list items
		menuItems = topMenu.find('a'),
		// Anchors corresponding to menu items
		scrollItems = menuItems.map(function () {
			var item = $($(this).attr('href'));
			if (item.length) {
				return item;
			}
		});

	// Bind click handler to menu items
	// so we can get a fancy scroll animation
	menuItems.click(function (e) {
		var href = $(this).attr('href'),
			offsetTop = href === '#' ? 0 : $(href).offset().top - topMenuHeight + 1;
		$('html, body').stop().animate(
			{
				scrollTop: offsetTop,
			},
			300
		);
		e.preventDefault();
	});

	// Bind to scroll
	$(window).scroll(function () {
		// Get container scroll position
		var fromTop = $(this).scrollTop() + topMenuHeight;

		// Get id of current scroll item
		var cur = scrollItems.map(function () {
			if ($(this).offset().top < fromTop) return this;
		});
		// Get the id of the current element
		cur = cur[cur.length - 1];
		var id = cur && cur.length ? cur[0].id : '';

		if (lastId !== id) {
			lastId = id;
			// Set/remove active class
			menuItems
				.parent()
				.removeClass('active')
				.end()
				.filter("[href='#" + id + "']")
				.parent()
				.addClass('active');
		}

		if ($(this).scrollTop() > 50) {
			$('#back-to-top').fadeIn();
		} else {
			$('#back-to-top').fadeOut();
		}
	});

	// scroll body to 0px on click
	$('#back-to-top').click(function () {
		$('body,html').animate(
			{
				scrollTop: 0,
			},
			400
		);
		return false;
	});

	var loading = $.loading();
	// Home Page Layered Approach
	$('#drk-btn, #agriota-btn, #krishico-btn, #agdata-btn').mouseover(function () {
		var id = $(this).attr('id');

		$(this).parent().parent().find('.active').removeClass('active');
		// $(this).addClass("active");
		if (id == 'krishico-btn') {
			$('.layer_1').show();
			$('.layer_2, .layer_3, .layer_4, .layer_all').hide();
		} else if (id == 'agriota-btn') {
			$('.layer_2').show();
			$('.layer_1, .layer_3, .layer_4, .layer_all').hide();
		} else if (id == 'agdata-btn') {
			$('.layer_3').show();
			$('.layer_1, .layer_2, .layer_4, .layer_all').hide();
		} else if (id == 'drk-btn') {
			$('.layer_4').show();
			$('.layer_1, .layer_2, .layer_3, .layer_all').hide();
		} else {
			$('.layer_all').show();
			$('.layer_1, .layer_2, .layer_3, .layer_4').hide();
		}
	});

	$('#drk-btn, #agriota-btn, #krishico-btn, #agdata-btn').mouseleave(function () {
		$('.layer_all').show();
		$('.layer_1, .layer_2, .layer_3, .layer_4').hide();
		$(this).parent().parent().find('.active').removeClass('active');
	});

	$.get('/footer.html', function (data) {
		$('footer').append(data);
	});

	// Business Type List
	if (document.getElementById('presentBusiness')) {
		$.ajax({
			url: baseUrl + '/buyer-pre-application-masters/bussiness-type-list',
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (data) {
				var dropdownValue = '';
				data.sort(function (a, b) {
					return a.name.localeCompare(b.name);
				});
				$.each(data, function (index, value) {
					dropdownValue += `<option value="${value.id}">${value.name}</option>`;
				});
				$('#presentBusiness').append(dropdownValue);
				loading.close();
			},

			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				// console.log('Error in Database');
			},
		});
	}

	// Type of Firm List
	if (document.getElementById('typeOfFirm')) {
		$.ajax({
			url: baseUrl + '/buyer-pre-application-masters/firm-type-list',
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (data) {
				loading.close();
				var dropdownValue = '';
				$.each(data, function (index, value) {
					dropdownValue += `<option value="${value.id}">${value.name}</option>`;
				});
				$('#typeOfFirm').append(dropdownValue);
			},

			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				// console.log('Error in Database');
			},
		});
	}

	// Currency List
	if (document.getElementById('currency')) {
		$.ajax({
			url: baseUrl + '/buyer-pre-application-masters/currency-list',
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (data) {
				loading.close();
				var dropdownValue = '';
				$.each(data, function (index, value) {
					dropdownValue += `<option value="${value.id}">${value.currencyCode} - ${value.currencyDecimalCode}</option>`;
				});
				$('#currency').append(dropdownValue);
			},
			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				// console.log('Error in Database');
			},
		});
	}

	// Industry List data
	if (document.getElementById('inputIndustry')) {
		$.ajax({
			url: baseUrl + '/partnership-request/getIndustry',
			type: 'GET',
			dataType: 'json',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (data) {
				loading.close();
				var dropdownValue = '';
				$.each(data, function (index, value) {
					dropdownValue += `<option value="${value.ID}">${value.Name}</option>`;
				});
				$('#inputIndustry').append(dropdownValue);
			},
			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				// console.log('Error in Database');
			},
		});
	}

	// Partnership Program List data
	if (document.getElementById('inputPartnershipProgram')) {
		$.ajax({
			url: baseUrl + '/partnership-request/getPartnershipProgram',
			type: 'GET',
			dataType: 'json',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (data) {
				loading.close();
				var dropdownValue = '';
				$.each(data, function (index, value) {
					dropdownValue += `<option value="${value.ID}">${value.Name}</option>`;
				});
				$('#inputPartnershipProgram').append(dropdownValue);
			},

			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				// console.log('Error in Database');
			},
		});
	}

	// Application Type List
	if (document.getElementById('applicationType')) {
		$.ajax({
			url: baseUrl + '/buyer-pre-application-masters/application-type-list',
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (data) {
				loading.close();
				var dropdownValue = '';

				data.sort(function (a, b) {
					return a.name.localeCompare(b.name);
				});
				$.each(data, function (index, value) {
					dropdownValue += `<option value="${value.id}">${value.name}</option>`;
				});
				$('#applicationType').append(dropdownValue);
			},

			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				// console.log('Error in Database');
			},
		});
	}

	// Applicant Type List
	if (document.getElementById('applicantType')) {
		$.ajax({
			url: baseUrl + '/buyer-pre-application-masters/applicant-type-list',
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (data) {
				loading.close();
				var dropdownValue = '';

				data.sort(function (a, b) {
					return a.name.localeCompare(b.name);
				});
				$.each(data, function (index, value) {
					dropdownValue += `<option value="${value.id}">${value.name}</option> `;
				});
				$('#applicantType').append(dropdownValue);
			},

			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				// console.log('Error in Database');
			},
		});
	}

	// commodity List
	if (document.getElementById('commodityInterest')) {
		$.ajax({
			url: baseUrl + '/buyer-pre-application-masters/commodity-list',
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (data) {
				loading.close();
				var dropdownValue = '';
				dropdownValue += `<option value="0">Other</option>`;
				$.each(data, function (index, value) {
					dropdownValue += `<option value="${value.id}">${value.name}</option>`;
				});
				$('#commodityInterest').append(dropdownValue);

				$('#commodityInterest').select2({
					placeholder: 'Select Commodities',
					// width: 'resolve',
					allowClear: true,
				});
			},

			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				// console.log('Error in Database');
			},
		});
	}

	// Designation List
	if (document.getElementById('directorsProprietorPartnerDesignation[0]')) {
		$.ajax({
			url: baseUrl + '/buyer-pre-application-masters/designation-list',
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (data) {
				loading.close();
				var dropdownValue = '';
				// var newData = data.sort(function(a, b) {
				//     // if (a.id < b.id) { return -1; }
				//     // if (a.id > b.id) { return 1; }
				//     // return 0;
				//     var nameA = a.name.toLowerCase(),
				//         nameB = b.name.toLowerCase();
				//     if (nameA < nameB) //sort string ascending
				//         return -1;
				//     if (nameA > nameB)
				//         return 1;
				//     return 0; //default return value (no sorting)
				// })

				data.sort(function (a, b) {
					return a.name.localeCompare(b.name);
				});
				$.each(data, function (index, value) {
					dropdownValue += `<option value="${value.id}">${value.name}</option>`;
				});
				$('#directorsProprietorPartnerDesignation\\[0\\], #directorsProprietorPartnerDesignation\\[1\\], #directorsProprietorPartnerDesignation\\[2\\], #directorsProprietorPartnerDesignation\\[3\\], #directorsProprietorPartnerDesignation\\[4\\], #directorsProprietorPartnerDesignation\\[5\\]').append(dropdownValue);
			},

			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				// console.log('Error in Database');
			},
		});
	}

	// Market Data tables
	if (document.getElementById('marketTable')) {
		getAllMarketRecords();
	}

	// if ($('.commo-con').length > 0) {

	//   var name = url.searchParams.get("name");
	//   var id = url.searchParams.get("id");

	//   if (name !== null && id !== null) {
	//     var name = url.searchParams.get("name");
	//     var id = url.searchParams.get("id");

	//     $(".commodity-view").hide();
	//     console.log(name);

	//     $(".commo-con").find("div#" + name).show();

	//     var get_name = $(".commo-con").find(name);
	//     if (get_name !== null) {
	//       var commMarkertPriceUrl = baseUrl + "/market?Id=" + id;
	//       getMarketPrice(commMarkertPriceUrl);
	//     }
	//     // $("#" + name + "").show();

	//     // $(".commo-con").load(name + ".html", function (responseTxt, statusTxt, xhr) {
	//     //   if (statusTxt == "success") {
	//     //     var commMarkertPriceUrl = baseUrl + "/market?Id=" + id;
	//     //     getMarketPrice(commMarkertPriceUrl);
	//     //   }
	//     //   if (statusTxt == "error") {
	//     //     console.log("Error: " + xhr.status + ": " + xhr.statusText);
	//     //   }
	//     // });

	//   } else {
	//     url.searchParams.set("name", "bajra");
	//     url.searchParams.set("id", 2);
	//     var newUrl = url.href;
	//     window.history.pushState({ path: newUrl }, '', newUrl);
	//     var id = url.searchParams.get("id");
	//     var name = url.searchParams.get("name");

	//     $(".commodity-view").hide();
	//     console.log(name);

	//     $(".commo-con").find("div#" + name).show();

	//     var get_name = $(".commo-con").find(name);
	//     if (get_name !== null) {
	//       var commMarkertPriceUrl = baseUrl + "/market?Id=" + id;
	//       getMarketPrice(commMarkertPriceUrl);
	//     }

	//     // var commMarkertPriceUrl = baseUrl + "/market?Id=" + id; // page=bajra and id=2 set default
	//     // getMarketPrice(commMarkertPriceUrl);
	//   }
	// }

	// $(".subList .nav-link").on('click', function () {
	//   url.searchParams.set("id", $(this).attr("data-id"));
	//   url.searchParams.set("name", $(this).attr("id"));
	//   var newUrl = url.href;
	//   window.history.pushState({ path: newUrl }, '', newUrl);

	//   var name = url.searchParams.get("name");
	//   var id = url.searchParams.get("id");

	//   $(".commodity-view").hide();
	//   console.log(name);

	//   $(".commo-con").find("div#" + name).show();

	//   var get_name = $(".commo-con").find(name);
	//   if (get_name !== null) {
	//     var commMarkertPriceUrl = baseUrl + "/market?Id=" + id;
	//     getMarketPrice(commMarkertPriceUrl);
	//   }

	//   // $(".commo-con").load($(this).attr("id") + ".html");
	//   // var commMarkertPriceUrl = baseUrl + "/market?Id=" + $(this).attr("data-id");
	//   // getMarketPrice(commMarkertPriceUrl);
	// });

	if ($('.commo-con').length > 0) {
		var name = url.searchParams.get('name');
		var id = url.searchParams.get('id');

		if (name !== null && id !== null) {
			var name = url.searchParams.get('name');
			var id = url.searchParams.get('id');
			$.ajax({
				type: 'GET',
				// cache: false,
				url: name + '.html',
				dataType: 'text',
				beforeSend: function (responseTxt, statusTxt, xhr) {
					loading.open();
				},
				success: function (data, statusTxt, xhr) {
					if (statusTxt == 'success') {
						$('.commo-con').html(data);
						getMarketPrice(baseUrl + '/market?Id=' + id);
					} else {
						console.log('Error: ' + xhr.status + ': ' + xhr.statusText);
						loading.close();
					}
				},
				error: function (xhr, textStatus, errorThrown) {
					console.log('Error: ' + xhr.status + ': ' + xhr.statusText);
					loading.close();
				},
			});
		} else {
			url.searchParams.set('name', 'bajra');
			url.searchParams.set('id', 2);
			var newUrl = url.href;
			window.history.pushState({ path: newUrl }, '', newUrl);
			var name = url.searchParams.get('name');
			var id = url.searchParams.get('id');
			$('.commo-con').empty();
			// $(".commo-con").load(name + ".html");
			$.ajax({
				type: 'GET',
				// cache: false,
				url: name + '.html',
				dataType: 'text',
				beforeSend: function (responseTxt, statusTxt, xhr) {
					loading.open();
				},
				success: function (data, statusTxt, xhr) {
					if (statusTxt == 'success') {
						$('.commo-con').html(data);
						getMarketPrice(baseUrl + '/market?Id=' + id);
					} else {
						console.log('Error: ' + xhr.status + ': ' + xhr.statusText);
						loading.close();
					}
				},
				error: function (xhr, textStatus, errorThrown) {
					console.log('Error: ' + xhr.status + ': ' + xhr.statusText);
					loading.close();
				},
			});
		}
	}

	$('.subList .nav-link').on('click', function () {
		url.searchParams.set('id', $(this).attr('data-id'));
		url.searchParams.set('name', $(this).attr('id'));
		var newUrl = url.href;
		window.history.pushState({ path: newUrl }, '', newUrl);
		$('.commo-con').empty();

		var name = url.searchParams.get('name');
		var id = url.searchParams.get('id');

		$.ajax({
			type: 'GET',
			// cache: false,
			url: name + '.html',
			dataType: 'text',
			beforeSend: function (responseTxt, statusTxt, xhr) {
				loading.open();
			},
			success: function (data, statusTxt, xhr) {
				if (statusTxt == 'success') {
					$('.commo-con').html(data);
					getMarketPrice(baseUrl + '/market?Id=' + id);
				} else {
					console.log('Error: ' + xhr.status + ': ' + xhr.statusText);
					loading.close();
				}
			},
			error: function (xhr, textStatus, errorThrown) {
				loading.close();
			},
		});
	});

	$('.apply-btn').on('click', function () {
		var post_name = $(this).parent().parent().find('.modal-title').text();
		// console.log(post_name);
		$('#apply_job .application-title').empty();
		$('#apply_job .application-title').append('Application For ' + post_name);
		$('#apply_job #post_name').val(post_name);
	});

	$('.required').prop('required', function () {
		return $(this).is(':visible');
	});

	// Contact Page Map height depend on side box
	$('#contact-map').css('height', $('.form-box').outerHeight(true));

	//Read more function
	$('.feature-box>.hover').on('click', function () {
		$(this).parent().find('p').toggleClass('display hide');
		$(this).parent().find('i').toggleClass('fa-angle-up fa-angle-down');
		$(this).text($(this).text() == 'Read More' ? 'Less' : 'Read More');
	});
	//read more function end

	/*sticky header*/
	var header = document.getElementById('js-header');
	var quick_links = document.getElementById('quick_links');
	window.onscroll = function () {
		if (header) {
			var sticky = header.offsetTop;
			if (window.pageYOffset > sticky) {
				header.classList.add('sticky');
				$('.banner, .privacy-container').css('margin-top', $('#js-header').outerHeight(true));
			} else {
				header.classList.remove('sticky');
				$('.banner, .privacy-container').css('margin-top', 0);
			}
		} else {
		}

		if (quick_links) {
			var sticky2 = quick_links.offsetTop;
			if (window.pageYOffset > sticky2) {
				quick_links.classList.add('sticky2');
				$('#quick_links').css('top', $('#js-header').outerHeight(true));
				$('.intro-con, .comm-summary').css('margin-top', $('#quick_links').outerHeight(true));
			} else {
				quick_links.classList.remove('sticky2');
				$('#quick_links').css('top', 0);
				$('.intro-con, .comm-summary').css('margin-top', 0);
			}
		} else {
		}
	};

	$('.nav-toggle').on('click', function () {
		$('.nav-menu').toggle();
		$('.nav-toggle .fas').toggleClass('fa-bars fa-times');
	});

	//Current Date Display end
	$(document).on('show', '.accordion', function (e) {
		//$('.accordion-heading i').toggleClass(' ');
		$(e.target).prev('.accordion-heading').addClass('accordion-opened');
	});

	$(document).on('hide', '.accordion', function (e) {
		$(this).find('.accordion-heading').not($(e.target)).removeClass('accordion-opened');
		//$('.accordion-heading i').toggleClass('fa-chevron-right fa-chevron-down');
	});

	$('.subList').hide();
	$('.mainList .nav-link').focus(function () {
		var data_id = $(this).data('id');
		// console.log(data_id);
		$('.closeBtn').show();
		$('.subList').each(function () {
			var el = $(this);
			if (el.attr('id') == data_id) {
				el.show();
			} else {
				el.hide();
			}
		});
	});

	$('.closeBtn, .subList .nav-link').on('click', function () {
		$('.subList').hide();
		$('.closeBtn').hide();
	});

	$('.business-address-check').change(function () {
		businessAddressCheck = $(this).val();
		console.log('businessAddressCheck: ' + businessAddressCheck);
		if (businessAddressCheck == 'Yes') {
			$('#businessAddress').val($('#registeredAddress').val()).trigger('blur');
			$('#businessAddress').prop('disabled', true);
		} else {
			$('#businessAddress').val('');
			$('#businessAddress').prop('disabled', false);
		}
	});

	$('.ownSpaceChecking').change(function () {
		ownSpaceChecking = $(this).val();
		console.log('ownSpaceChecking: ' + ownSpaceChecking);
		if (ownSpaceChecking == 'No') {
			$('.ownSpaceChecked').hide();
			$('#usableArea, #officeAddress').prop('disabled', true);
		} else {
			$('.ownSpaceChecked').show();
			$('#usableArea, #officeAddress').prop('disabled', false);
		}
	});

	$('.businessPremisesChecking').change(function () {
		businessPremisesChecking = $(this).val();
		console.log('businessPremisesChecking: ' + businessPremisesChecking);
		if (businessPremisesChecking == 'No') {
			$('.businessPremisesChecked').hide();
			$('#presentBusiness, #presentEmployer, #product, #typeOfFirm, #yearOfEstablishment, #currency, #netWorthAppDate, #location').prop('disabled', true);
		} else {
			$('.businessPremisesChecked').show();
			$('#presentBusiness, #presentEmployer, #product, #typeOfFirm, #yearOfEstablishment, #currency, #netWorthAppDate, #location').prop('disabled', false);
		}
	});

	$('#origin').change(function () {
		origin = $(this).val();
		$('#applicantType').val('').trigger('change');
		if (origin == 'International') {
			$('.applicantTypeCheck').hide();
			$('.originCheck').hide();
			$('.originInternationCheck').show();
		} else if (origin == 'Indian') {
			$('.originCheck').show();
			$('.originInternationCheck').hide();
		} else {
			$('.applicantTypeCheck').show();
		}
	});

	$('#applicantType').change(function () {
		applicantType = $(this).val();
		origin = $('#origin').val();
		if (origin == 'International' || applicantType == '6') {
			$('.applicantTypeCheck').hide();
		} else {
			$('.applicantTypeCheck').show();
		}
	});

	$('.natureOfBusiness').change(function () {
		natureOfBusiness = $(this).val();
		console.log('natureOfBusiness: ' + natureOfBusiness);
		if (natureOfBusiness == 'Non-Agriculture') {
			$('.nonAgricultureBusinessName').show();
		} else {
			$('.nonAgricultureBusinessName').hide();
		}
	});

	// Trim space of all input form fields
	$('input[type=text],input[type=email],input[type=tel],input[type=number],input[type=url],input[type=search],input[type=date],input[type=password],textarea').on('blur', function () {
		var value = $.trim($(this).val());
		value = value.replace(/\s\s+/g, ' ');
		$(this).val(value);
	});

	$('#designation').change(function () {
		var inputValue = $(this).val();
		if (inputValue == 6) {
			$('.otherDesignationcheck').show();
		} else {
			$('.otherDesignationcheck').hide();
		}
	});

	$('#commodityMarketInfoList').change(function () {
		var commodityMarketId = $(this).val();
		var commodityName = $(this).find('option:selected').text();
		commodityName = commodityName.split('(');
		commodityName = commodityName[0].replace(/ /g, '-');
		var isEndsWithcommodityName = commodityName.endsWith('-');
		if (isEndsWithcommodityName) {
			commodityName = commodityName.substring(0, commodityName.length - 1);
		}

		url.searchParams.set('name', commodityName);
		url.searchParams.set('id', commodityMarketId);
		url.searchParams.delete('stateCode', stateCode);
		var newUrl = url.href;
		window.history.pushState({ path: newUrl }, '', newUrl);

		if (commodityMarketId) {
			getAllMarketRecords();
		}
	});

	$('#stateMarketInfoList').change(function () {
		var stateCode = $(this).val();
		var commodityId = $('#commodityMarketInfoList').val();
		url.searchParams.set('stateCode', stateCode);
		url.searchParams.set('id', commodityId);
		var newUrl = url.href;
		window.history.pushState({ path: newUrl }, '', newUrl);
		if (stateCode) {
			stateWiseMarketDataFilter(stateCode, commodityId);
		}
	});

	if ($('#stateMarketInfoList')) {
		url.searchParams.get('stateCode', stateCode);
		url.searchParams.get('id', commodityId);

		var stateCode;
		var commodityId;
		if (stateCode && commodityId) {
			stateWiseMarketDataFilter(stateCode, commodityId);
		}
	}

	// Commoditywise Stress List using Ticker Data
	if (document.getElementById('drkTicker')) {
		$.ajax({
			url: baseUrl + '/ticker/getCommodityWiseStress',
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (data) {
				if (data != null) {
					var stressList = '';
					$.each(data, function (index, value) {
						stressList += '<li><strong>Commodity: </strong>' + value.Commodity + ', <strong>Phenophase: </strong> ' + value.Phenophase + ', <strong>Stress name: </strong> ' + value.Stress + '</li>';
					});
					$('#drkTicker').show();
					$('#drkTicker .js-conveyor-1 ul').append(stressList);

					if (jQuery().jConveyorTicker) {
						$('.js-conveyor-1').jConveyorTicker({
							anim_duration: 200, // Specify the time (in milliseconds) the animation takes to move 10 pixels
							reverse_elm: true, // Enable the use of the reverse animation trigger HTML element
							force_loop: false, // Force the initialization even if the items are too small in total width
							start_paused: false, // Initialize in a paused state
						});
					}
				} else {
					console.log('Commoditywise Stress List Ticker Data Not found');
				}
				loading.close();
			},
			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				console.log('Error in Commodity Stress Ticker Data');
				$('#drkTicker').hide();
			},
		});
	}

	// Commodities Market Price Ticker Data
	if (document.getElementById('agriotaTicker')) {
		$.ajax({
			url: baseUrl + '/ticker/getMarketPriceListDateWise',
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (response) {

				if (response.data.length != 0) {
					var ComMarketPrice = '';
					// console.log(response.data[0].MarketName);
					$.each(response.data, function (index, value) {
						ComMarketPrice += "<li><strong>'" + value.Commodity + "' </strong> <span class='b'>Market Name : </span>" + value.MarketName + ", &nbsp;<span class='b'>Variety : </span>" + value.Variety + ", &nbsp;<span class='b'>Min Price : </span>" + value.MinPrice + " &nbsp;<span class='b'>Max Price : </span>" + value.MaxPrice + " &nbsp;<span class='b'>Modal Price : </span>" + value.ModalPrice + '</li>';
					});

					$('#agriotaTicker').show();
					$('#agriotaTicker .current-date').append(response.date);
					$('#agriotaTicker .js-conveyor-1 ul').append(ComMarketPrice);

					if (jQuery().jConveyorTicker) {
						$('.js-conveyor-1').jConveyorTicker({
							anim_duration: 400, // Specify the time (in milliseconds) the animation takes to move 10 pixels
							reverse_elm: true, // Enable the use of the reverse animation trigger HTML element
							force_loop: false, // Force the initialization even if the items are too small in total width
							start_paused: false, // Initialize in a paused state
						});
					}
				} else {
					console.log('Commodities Market Price Ticker Data Not found');
				}
				loading.close();
			},
			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				console.log('Error in Commodities Market Price Ticker Data');
				$('#agriotaTicker').hide();
			},
		});
	}

	//Reports API Data
	if ($('.report-list').length > 0) {
		$.ajax({
			url: baseUrl + '/reports',
			// url: "http://192.168.0.50:8080/site/reports",
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (response) {
				//Agriota reports
				if (document.getElementById('agriotaReportList')) {
					if (response.Agriota != undefined) {
						var data = response.Agriota;
						var reportList = '';
						var authenticateReportList = '';
						$.each(data, function (index, value) {
							if (value.authenticate == 'No') {
								reportList += `<li>` + value.title + `<div class="download-section"><div><a href="` + value.fileUrl + `" target="_blank"><i class="fas fa-file-download"></i></a></div></div></li>`;
							}

							if (value.authenticate == 'Yes') {
								authenticateReportList += `<li>` + value.title + `<div class="download-section"><div><a href="cropdata-reports.html?id=` + value.id + `"><i class="fas fa-unlock-alt"></i></a></div></div></li>`;
							}
						});
						$('#agriotaReportList').append(reportList + authenticateReportList);
					} else {
						$('#agriotaReportList').append("<li class='border-0'>Data Not Available.</li>");
					}

					loading.close();
				}

				//DrKrishi reports
				if (document.getElementById('drkReportList')) {
					if (response.DrKrishi != undefined) {
						var data = response.DrKrishi;
						var reportList = '';
						var authenticateReportList = '';
						$.each(data, function (index, value) {
							if (value.authenticate == 'No') {
								reportList += `<li>` + value.title + `<div class="download-section"><div><a href="` + value.fileUrl + `" target="_blank"><i class="fas fa-file-download"></i></a></div></div></li>`;
							}

							if (value.authenticate == 'Yes') {
								authenticateReportList += `<li>` + value.title + `<div class="download-section"><div><a href="cropdata-reports.html?id=` + value.id + `"><i class="fas fa-unlock-alt"></i></a></div></div></li>`;
							}
						});
						$('#drkReportList').append(reportList + authenticateReportList);
					} else {
						$('#drkReportList').append("<li class='border-0'>Data Not Available.</li>");
					}
					loading.close();
				}

				//AgData reports
				if (document.getElementById('agdataReportList')) {
					if (response.AgData != undefined) {
						var data = response.AgData;
						var reportList = '';
						var authenticateReportList = '';
						$.each(data, function (index, value) {
							if (value.authenticate == 'No') {
								reportList += `<li>` + value.title + `<div class="download-section"><div><a href="` + value.fileUrl + `" target="_blank"><i class="fas fa-file-download"></i></a></div></div></li>`;
							}

							if (value.authenticate == 'Yes') {
								authenticateReportList += `<li>` + value.title + `<div class="download-section"><div><a href="cropdata-reports.html?id=` + value.id + `"><i class="fas fa-unlock-alt"></i></a></div></div></li>`;
							}
						});
						$('#agdataReportList').append(reportList + authenticateReportList);
					} else {
						$('#agdataReportList').append("<li class='border-0'>Data Not Available.</li>");
					}
					loading.close();
				}

				//KrishiCo reports
				if (document.getElementById('krishiCoReportList')) {
					if (response.KrishiCo != undefined) {
						var data = response.KrishiCo;
						var reportList = '';
						var authenticateReportList = '';
						$.each(data, function (index, value) {
							if (value.authenticate == 'No') {
								reportList += `<li>` + value.title + `<div class="download-section"><div><a href="` + value.fileUrl + `" target="_blank"><i class="fas fa-file-download"></i></a></div></div></li>`;
							}

							if (value.authenticate == 'Yes') {
								authenticateReportList += `<li>` + value.title + `<div class="download-section"><div><a href="cropdata-reports.html?id=` + value.id + `"><i class="fas fa-unlock-alt"></i></a></div></div></li>`;
							}
						});
						$('#krishiCoReportList').append(reportList + authenticateReportList);
					} else {
						$('#krishiCoReportList').append("<li class='border-0'>Data Not Available.</li>");
					}
					loading.close();
				}
			},
			error: function (xhr, textStatus, errorThrown) {
				console.log(xhr.responseJSON.error);
				$('.report-list').append("<p class='m-0>" + xhr.responseJSON.error + '</p>');
				loading.close();
			},
		});
	}

	//All News section
	if ($('.news-and-report-container').length > 0) {
		$.ajax({
			url: baseUrl + '/news',
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (response) {
				//DRK News

				if (document.getElementById('drkNewsList')) {
					if (response.DrKrishi != undefined && response.DrKrishi != null) {
						console.log(response.DrKrishi);
						var newsList = '';

						var filterDrKrishi = response.DrKrishi.sort(function (a, b) {
							const date1 = new Date(a.publishedDate);
							const date2 = new Date(b.publishedDate);
							if (date1 > date2) return -1;
							else if (date1 == date2) return 0;
							else return 1;
						});

						$.each(filterDrKrishi, function (index, value) {
							// console.log(value);
							newsList += `<div class="col-12 col-sm-12 col-md-12 col-lg-6 col-xl-6 content news-thumb"><a href="` + value.externalUrl + `" target="_blank"><div class="img-thumb" style="background-image: url(` + ($.trim(value.imageUrl) == '' ? `images/news-default.jpg` : value.imageUrl) + `);"></div><div class="news-content"><h5 class="title">` + value.title + `</h5><div class="date">` + value.publishedDate + `</div><div class="author">` + value.source + `</div></div></a></div>`;
							newsCount++;
						});
						if (newsCount < 10) {
							$('#seeMore').hide();
						}
						$('#drkNewsList').append(newsList);
						$('.content').slice(0, 10).show();
					} else {
						$('#drkNewsList').append("<div class='col'>Data Not Available.</div>");
						$('#seeMore').hide();
					}
					loading.close();
				}

				//AgData News
				if (document.getElementById('agdataNewsList')) {
					if (response.AgData != undefined && response.AgData != null && response.AgData.length != null && response.AgData.length > 0) {
						var newsList = '';

						var filterAgData = response.AgData.sort(function (a, b) {
							const date1 = new Date(a.publishedDate);
							const date2 = new Date(b.publishedDate);
							if (date1 > date2) return -1;
							else if (date1 == date2) return 0;
							else return 1;
						});

						$.each(filterAgData, function (index, value) {
							// console.log(value);
							newsList += `<div class="col-12 col-sm-12 col-md-12 col-lg-6 col-xl-6 content news-thumb"><a href="` + value.externalUrl + `" target="_blank"><div class="img-thumb" style="background-image: url(` + ($.trim(value.imageUrl) == '' ? `images/news-default.jpg` : value.imageUrl) + `);"></div><div class="news-content"><h5 class="title">` + value.title + `</h5><div class="date">` + value.publishedDate + `</div><div class="author">` + value.source + `</div></div></a></div>`;
							newsCount++;
						});
						if (newsCount < 10) {
							$('#seeMore').hide();
						}
						$('#agdataNewsList').append(newsList);
						$('.content').slice(0, 10).show();
					} else {
						$('#agdataNewsList').append("<div class='col'>Data Not Available.</div>");
						$('#seeMore').hide();
					}
					loading.close();
				}

				//Agriota News
				if (document.getElementById('agriotaNewsList')) {
					if (response.Agriota != undefined && response.Agriota != null && response.Agriota.length != null && response.Agriota.length > 0) {
						var newsList = '';

						var filterAgriota = response.Agriota.sort(function (a, b) {
							const date1 = new Date(a.publishedDate);
							const date2 = new Date(b.publishedDate);
							if (date1 > date2) return -1;
							else if (date1 == date2) return 0;
							else return 1;
						});

						$.each(filterAgriota, function (index, value) {
							// console.log(value);
							newsList += `<div class="col-12 col-sm-12 col-md-12 col-lg-6 col-xl-6 content news-thumb"><a href="` + value.externalUrl + `" target="_blank"><div class="img-thumb" style="background-image: url(` + ($.trim(value.imageUrl) == '' ? `images/news-default.jpg` : value.imageUrl) + `);"></div><div class="news-content"><h5 class="title">` + value.title + `</h5><div class="date">` + value.publishedDate + `</div><div class="author">` + value.source + `</div></div></a></div>`;
							newsCount++;
						});
						if (newsCount < 10) {
							$('#seeMore').hide();
						}
						$('#agriotaNewsList').append(newsList);
						$('.content').slice(0, 10).show();
					} else {
						$('#agriotaNewsList').append("<div class='col'>Data Not Available.</div>");
						$('#seeMore').hide();
					}
					loading.close();
				}

				//krishiCo News
				if (document.getElementById('krishiCoNewsList')) {
					if (response.KrishiCo != undefined && response.KrishiCo != null && response.KrishiCo.length != null && response.KrishiCo.length > 0) {
						var newsList = '';

						var filterKrishiCo = response.KrishiCo.sort(function (a, b) {
							const date1 = new Date(a.publishedDate);
							const date2 = new Date(b.publishedDate);
							if (date1 > date2) return -1;
							else if (date1 == date2) return 0;
							else return 1;
						});

						$.each(filterKrishiCo, function (index, value) {
							// console.log(value);
							newsList += `<div class="col-12 col-sm-12 col-md-12 col-lg-6 col-xl-6 content news-thumb"><a href="` + value.externalUrl + `" target="_blank"><div class="img-thumb" style="background-image: url(` + ($.trim(value.imageUrl) == '' ? `images/news-default.jpg` : value.imageUrl) + `);"></div><div class="news-content"><h5 class="title">` + value.title + `</h5><div class="date">` + value.publishedDate + `</div><div class="author">` + value.source + `</div></div></a></div>`;
							newsCount++;
						});
						if (newsCount < 10) {
							$('#seeMore').hide();
						}
						$('#krishiCoNewsList').append(newsList);
						$('.content').slice(0, 10).show();
					} else {
						$('#krishiCoNewsList').append("<div class='col'>Data Not Available.</div>");
						$('#seeMore').hide();
					}
					loading.close();
				}
			},
			error: function (xhr, textStatus, errorThrown) {
				console.log(xhr.responseJSON.error);
				$('.news-and-report-container .card-body .row').append("<div class='col-xl-12'><p class='m-0>" + xhr.responseJSON.error + '</p></div>');
				loading.close();
			},
		});
	}

	// News Page
	if ($('.general_news_section').length > 0) {
		$.ajax({
			url: baseUrl + '/news',
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (response) {
				var cropDataNewsList = '';
				var cropDataNewsListFilter = '';
				var LatestNewsList = '';
				var LatestNewsListFilter = '';
				var genNewsList = '';
				var allGenNews = [];

				if (response.DrKrishi == undefined && response.DrKrishi == null) {
					response.DrKrishi = [];
				} else {
					allGenNews = $.merge(allGenNews, response.DrKrishi);
				}
				if (response.AgData == undefined && response.AgData == null) {
					response.AgData = [];
				} else {
					allGenNews = $.merge(allGenNews, response.AgData);
				}
				if (response.Agriota == undefined && response.Agriota == null) {
					response.Agriota = [];
				} else {
					allGenNews = $.merge(allGenNews, response.Agriota);
				}
				if (response.KrishiCo == undefined && response.KrishiCo == null) {
					response.KrishiCo = [];
				} else {
					allGenNews = $.merge(allGenNews, response.KrishiCo);
				}

				// var allGenNews = $.merge($.merge($.merge($.merge([], response.DrKrishi), response.AgData), response.Agriota), response.KrishiCo)

				if (response.CropData != undefined && response.CropData != null) {
					cropDataNewsListFilter = response.CropData.sort(function (a, b) {
						const item1 = new Date(a.priority);
						const item2 = new Date(b.priority);
						if (item1 < item2) return -1;
						else if (item1 == item2) return 0;
						else return 1;
					});
				} else {
					console.log('CropData News Data Not Found');
				}

				if (response.LatestNews != undefined && response.LatestNews != null) {
					LatestNewsListFilter = response.LatestNews.sort(function (a, b) {
						const item1 = new Date(a.priority);
						const item2 = new Date(b.priority);
						if (item1 < item2) return -1;
						else if (item1 == item2) return 0;
						else return 1;
					});
				} else {
					console.log('LatestNews Data Not Found');
				}

				if (allGenNews != undefined && allGenNews != null) {
					allGenNews.sort(function (a, b) {
						const date1 = new Date(a.publishedDate);
						const date2 = new Date(b.publishedDate);
						if (date1 > date2) return -1;
						else if (date1 == date2) return 0;
						else return 1;
						// return a.publishedDate.localeCompare(b.publishedDate);
					});
				} else {
					console.log('allGenNews Data Not Found');
				}

				//Latest News

				if (response.LatestNews != undefined && response.LatestNews != null) {
					var latestNewsCounter = 0;
					$.each(LatestNewsListFilter, function (index, value) {
						var date = value.date;
						if (date == '01' || date == '21' || date == '31') {
							date = value.date + '<sup>st</sup>';
						} else if (date == '02' || date == '22') {
							date = value.date + '<sup>nd</sup>';
						} else if (date == '03' || date == '23') {
							date = value.date + '<sup>rd</sup>';
						} else {
							date = value.date + '<sup>th</sup>';
						}
						LatestNewsList += `<div class="item"><div class="l_news_box"><a href="` + value.externalUrl + `" target="_blank" class="thumb" style="background-image: url(` + value.imageUrl + `);" title="` + value.title + `"><div class="date"><span>` + date + `</span>` + value.publishedDate + `</div><div class="inner-div"><h6 class="news-title">` + value.title + `</h6></div></a></div></div>`;
						latestNewsCounter++;
					});
					$('.owl-carousel').html(LatestNewsList);
					$('.owl-carousel').owlCarousel({
						autoPlay: 3000, //Set AutoPlay to 3 seconds
						margin: 10,
						loop: true,
						navigation: true,
						navText: ["<div class='nav-btn prev-slide'></div>", "<div class='nav-btn next-slide'></div>"],
						items: 2,
						itemsDesktop: [1370, 2],
						itemsDesktopSmall: [979, 1],
					});
					if (latestNewsCounter > 16) {
						$('.owl-pagination').hide();
					}
				} else {
					console.log('LatestNews Data Not Found');
					$('.owl-carousel').html('Data Not Found').show().addClass('text-center');
				}

				if (cropDataNewsListFilter != undefined && cropDataNewsListFilter != null) {
					//Cropdata News
					$.each(cropDataNewsListFilter, function (index, value) {
						cropDataNewsList += `<li><a href="` + value.externalUrl + `" target="_blank"><h6 class="news-title">` + value.title + ` - <span style="font-size: 10px;padding: 3px 8px;border-radius: 2px;background-color: #efefef;color: #052494;">` + value.source + `</span></h6></a></li>`;
					});
					$('#cropdataNewsList').html(cropDataNewsList);
				} else {
					console.log('cropdataNewsList Not Found');
				}

				//General News
				if (allGenNews != undefined && allGenNews != null) {
					$.each(allGenNews, function (index, value) {
						genNewsList += `<li><a href="` + value.externalUrl + `" target="_blank"><h6 class="news-title">` + value.title + ` - <span style="font-size: 10px;padding: 3px 8px;border-radius: 2px;background-color: #efefef;color: #052494;">` + value.source + `</span></h6></a></li>`;
					});
					$('#agricultureNewsList').html(genNewsList);
					loading.close();
				} else {
					console.log('allGenNews Data Not found');
					$('#agricultureNewsList').html('Data Not found');
				}
			},
			error: function (xhr, textStatus, errorThrown) {
				console.log(xhr.responseJSON.error);
				$('.news-and-report-container .card-body .row').append("<div class='col-xl-12'><p class='m-0>" + xhr.responseJSON.error + '</p></div>');
				loading.close();
			},
		});
	}

	// Job Post Page
	if ($('.post-view').length > 0) {
		jobPost();
	}

	$('#loadMorePost').on('click', function (e) {
		jobPost();
	});

	function jobPost() {
		$.ajax({
			url: baseUrl + '/opportunities/paginatedList?page=' + careerPaginationLength,
			// url: "http://192.168.0.50:8080/site/opportunities/paginatedList?page=" + careerPaginationLength,
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (response) {
				// console.log(response.content);
				var jobPostList = '';
				var desc = '';

				$.each(response.content, function (index, value) {
					desc = value.description.replace(/(<p>|<\/p>)/g, ' ');
					jobPostList +=
						`<div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-6 mb-4 animated animatedFadeInUp fadeInUp">
                          <a href="job-details.html?id=` +
						value.id +
						`" class="post-con">
                            <h5 class="post-name">` +
						value.position +
						`</h5>
                            <div class="req">` +
						value.experience +
						`</div>
                            <p class="sortDesc post-desc m-0">` +
						desc +
						`</p>
                            <p class="m-0 post-desc"><strong>Location: </strong>` +
						value.location +
						`</p>
                          </a>
                        </div>`;
				});
				$('#postList').append(jobPostList);

				jQuery('.sortDesc').each(function () {
					var text = jQuery(this).text();
					if (text.length > 100) {
						jQuery(this).text(text.substr(0, text.lastIndexOf(' ', 150)) + '...');
					}
				});

				if (response.last == false) {
					careerPaginationLength = careerPaginationLength + 1;
					// console.log(careerPaginationLength);
					$('#loadMorePost').show();
				} else {
					// console.log(careerPaginationLength);
					$('#loadMorePost').hide();
				}
				loading.close();
			},
			error: function (xhr, textStatus, errorThrown) {
				console.log(xhr.responseJSON.error);
				loading.close();
			},
		});
	}

	// Get Market Price by commodity ID (Single record)
	function getMarketPrice(commMarkertPriceUrl) {
		$.ajax({
			url: commMarkertPriceUrl,
			type: 'GET',
			beforeSend: function (xhr) {
				$('#tabledata').empty();
			},
			success: function (data) {
				var marketData;
				// let stateCode = data.stateCode;
				// let districtCode = data.districtCode;
				// let stateName = data.stateName;
				// let districtName = data.districtName;
				// let marketID = data.marketID;
				// let marketName = data.marketName;
				let commodityID = data.commodityID;
				// let commodityName = data.commodityName;
				// let pricingAgriVarietyID = data.pricingAgriVarietyID;
				// let pricingAgriVarietyName = data.pricingAgriVarietyName;
				// let minPrice = data.minPrice;
				// let maxPrice = data.maxPrice;
				// let modalPrice = data.modalPrice;
				// let minPriceChangePecent = data.minPriceChangePercentage;
				// let maxPriceChangePecent = data.maxPriceChangePercentage;
				// let modalPriceChangePecent = data.modalPriceChangePercentage;
				// console.log(data.stateName, data.districtName, data.marketName, data.pricingAgriVarietyID, 'chart', data.minPrice, data.maxPrice, data.modalPrice, data.minPriceChangePecent, data.maxPriceChangePecent, data.modalPriceChangePecent);
				// $("#tabledata").append('<tr><td class="stateName">' + data.stateName + '</td><td class="districtName">' + data.districtName + '</td><td class="marketName">' + data.marketName + '</td><td class="pricingAgriVariety">' + data.pricingAgriVarietyName + '</td><td class="text-center charts"><img alt="" data-stateId="' + data.stateCode + '" data-districtId="' + data.districtCode + '" data-commodityId="' + commodityID + '" data-verietyId="' + data.pricingAgriVarietyID + '" data-marketId="' + data.marketID + '" src="images/icon/trend-graph.svg" width="25" onclick="marketChartData(event);" class="img-fluid allowed"></td><td class="minPrice">' + data.minPrice + '</td><td class="maxPrice">' + data.maxPrice + '</td><td class="modalPrice">' + data.modalPrice + '</td><td class="minPriceChangePercentage">' + data.minPriceChangePercentage + '</td><td class="maxPriceChangePercentage">' + data.maxPriceChangePercentage + '</td><td class="modalPriceChangePercentage">' + data.modalPriceChangePercentage + "</td></tr>");

				marketData += '<tr><td class="stateName">' + data.stateName + '</td><td class="districtName">' + data.districtName + '</td><td class="marketName">' + data.marketName + '</td><td class="pricingAgriVariety">' + data.pricingAgriVarietyName + '</td><td class="text-center charts"><img alt="" data-stateId="' + data.stateCode + '" data-districtId="' + data.districtCode + '" data-commodityId="' + commodityID + '" data-verietyId="' + data.pricingAgriVarietyID + '" data-marketId="' + data.marketID + '" src="images/icon/trend-graph.svg" width="25" onclick="marketChartData(event);" class="img-fluid allowed"></td><td class="minPrice">' + data.minPrice + '</td><td class="maxPrice">' + data.maxPrice + '</td><td class="modalPrice">' + data.modalPrice + '</td><td class="minPriceChangePercentage">' + data.minPriceChangePercentage + '</td><td class="maxPriceChangePercentage">' + data.maxPriceChangePercentage + '</td><td class="modalPriceChangePercentage">' + data.modalPriceChangePercentage + '</td></tr>';
				$('#tabledata').append(marketData);
				$('#more-prices-btn a').attr('data-info', commodityID);
				loading.close();
			},

			error: function (xhr, textStatus, errorThrown) {
				$('#tabledata').empty();
				$('#tabledata').append('<tr><td colspan="12"><p class="my-2">' + xhr.responseJSON.error + '</p></td></tr>');
				loading.close();
			},
		});
	}

	// Job Details Page
	if ($('.job-details').length > 0) {
		var id = url.searchParams.get('id');
		var postDetailsPageUrl = baseUrl + '/opportunities/id/' + id;
		// var postDetailsPageUrl = "http://192.168.0.50:8080/site/opportunities/id/" + id;
		$.ajax({
			url: postDetailsPageUrl,
			type: 'GET',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (response) {
				var jobPostDetails = '';
				if (response.id == undefined) {
					$('.shadow.rounded').hide();
				} else {
					jobPostDetails +=
						`
        <thead>
              <tr>
                <th colspan="2">
                  <div class="d-flex justify-content-between align-items-start">
                    <h4 class="jobPosition m-0 text-capitalize">` +
						response.position +
						`</h4>
                    <span style="padding: 7px 15px; border-radius: 2px; background-color: #efefef; color: #052494; display: inline-block; font-weight: bold;">Publish Date: ` +
						response.postedOn +
						`</span>
                  </div>
                </th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><p><strong>Education: </strong></p></td>
                <td><p>` +
						response.education +
						`</p></td>
              </tr>

              <tr>
                <td><p><strong>Profile: </strong></p></td>
                <td><div class="profile">` +
						response.profile +
						`</div ></td >
              </tr >

              <tr>
                <td><p><strong>Description: </strong></p></td>
                <td><div class="description">` +
						response.description +
						`</div></td>
              </tr>

              <tr>
                <td><p><strong>Experience: </strong></p></td>
                <td><p>` +
						response.experience +
						`</p></td>
              </tr>

              <tr>
                <td><p><strong>Location: </strong></p></td>
                <td><p>` +
						response.location +
						`</p></td>
              </tr>

              <tr>
                <td><p><strong>Department: </strong></p></td>
                <td><p>` +
						response.department +
						`</p></td>
              </tr>

              <tr>
                <td><p><strong>Remuneration: </strong></p></td>
                <td><p>` +
						response.remuneration +
						`</p></td>
              </tr>

              <tr>
                <td><p><strong>Job Posted On: </strong></p></td>
                <td><p>` +
						response.postedOn +
						`</p></td>
              </tr>

              <tr>
                <td><p><strong>Apply To: </strong></p></td>
                <td><p>` +
						response.applyTo +
						`</p></td>
              </tr>
            </tbody > `;

					$('.jobDetailsViewTable').append(jobPostDetails);
					// $("#postDate").append("Job Publish Date: " + response.postedOn);
					$('.jobApply').attr('href', 'mailto:jobs@cropdata.in?subject=Job Application for ' + response.position);
					$('.application-title').html('Job Application for ' + response.position);
					$('#post_id').val(response.id);
				}

				loading.close();
			},
			error: function (xhr, textStatus, errorThrown) {
				console.log(xhr.responseJSON.error);
				$('.shadow.rounded').hide();
				loading.close();
			},
		});
	}

	if ($('.jobPosition').length > 0) {
		$('#postDetailModal .modal-footer').append(`<a href="#apply_job" data-dismiss="modal" aria-label="Close" data-toggle="modal" class="apply-btn btn btn-sm btn-primary">Apply Now</a>`);
	}

	$('.scroll').on('click', function (e) {
		e.preventDefault();
		var offset = 0;
		var target = this.hash;
		if ($(this).data('offset') != undefined) offset = $(this).data('offset');
		$('html, body')
			.stop()
			.animate(
				{
					scrollTop: $(target).offset().top - offset,
				},
				500,
				'swing',
				function () {
					// window.location.hash = target;
				}
			);
	});

	if (window.location.hash) {
		setTimeout(function () {
			$('html, body').scrollTop(0).show();
			$('html, body').animate(
				{
					scrollTop: $(window.location.hash).offset().top,
				},
				1000
			);
		}, 0);
	} else {
		$('html, body').show();
	}
	$('#loading').fadeOut();
});

// Get Commodity List
if (document.getElementById('commodityMarketInfoList')) {
	$('#commodityMarketInfoList').empty();
	$.ajax({
		url: baseUrl + '/buyer-pre-application-masters/active-commodity-list',
		type: 'GET',
		dataType: 'json',
		success: function (data) {
			var dropdownValue = '';
			$.each(data, function (index, value) {
				dropdownValue += `<option value="${value.id}">${value.name}</options>`;
			});

			$('#commodityMarketInfoList').append(`<option selected="" disabled="">Select Commodity</option>`);
			$('#commodityMarketInfoList').append(dropdownValue);
		},

		error: function (xhr, textStatus, errorThrown) {
			console.log('Error in Database');
		},
	});
}

function getAllMarketRecords() {
	var loading = $.loading();

	var name = url.searchParams.get('name');
	var id = url.searchParams.get('id');

	if (name != null && id != null) {
		name = url.searchParams.get('name');
		id = url.searchParams.get('id');
	} else {
		url.searchParams.set('name', 'bajra');
		url.searchParams.set('id', 2);
		var newUrl = url.href;
		window.history.pushState({ path: newUrl }, '', newUrl);
		id = url.searchParams.get('id');
	}

	$.ajax({
		url: baseUrl + '/market-records?Id=' + id,
		type: 'GET',
		beforeSend: function (xhr) {
			loading.open();
			$('#marketTable').empty();
			$('#stateMarketInfoList').empty();
		},
		success: function (response) {
			var trHTML = '';

			var stateDataArr = [];
			// console.log(response);
			$.each(response, function (i, item) {
				trHTML += '<tr><td>' + item.arrivalDate + '</td><td class="text-left">' + item.stateName + '</td><td class="text-left">' + item.districtName + '</td><td class="text-left">' + item.marketName + '</td><td class="text-left">' + item.varietyName + '</td><td class="text-center charts"><img alt="" data-stateId="' + item.stateCode + '" data-districtId="' + item.districtCode + '" data-commodityId="' + item.commodityID + '" data-verietyId="' + item.pricingAgriVarietyID + '" data-marketId="' + item.marketID + '" src="images/icon/trend-graph.svg" width="25" onclick="marketChartData(event);" class="img-fluid allowed"></td><td>' + item.minPrice + '</td><td>' + item.maxPrice + '</td><td>' + item.modalPrice + '</td><td>' + item.minPriceChangePercentage + '</td><td>' + item.maxPriceChangePercentage + '</td><td>' + item.modalPriceChangePercentage + '</td></tr>';

				var stateData = {
					stateCode: '',
					stateName: '',
				};
				stateData.stateCode = item.stateCode;
				stateData.stateName = item.stateName;
				stateDataArr[i] = stateData;
			});

			stateDataArr = Array.from(new Set(stateDataArr.map((s) => s.stateCode))).map((stateCode) => {
				return {
					stateCode: stateCode,
					stateName: stateDataArr.find((s) => s.stateCode === stateCode).stateName,
				};
			});

			stateDataArr.sort(function (a, b) {
				return a.stateName.localeCompare(b.stateName);
			});

			// console.log(stateDataArr);
			var stateDropdown = '';
			$.each(stateDataArr, function (i, value) {
				stateDropdown += `<option value="${value.stateCode}">${value.stateName}</option>`;
			});

			$('#marketTable').append(trHTML);
			$('#stateMarketInfoList').append(`<option value ="0" selected = "">Select All</option>` + stateDropdown);
			$('#commodityMarketInfoList option[value="' + id + '"]').attr('selected', 'selected');

			var stateCode = url.searchParams.get('stateCode');
			var commodityId = url.searchParams.get('id');
			if (stateCode && commodityId) {
				stateWiseMarketDataFilter(stateCode, commodityId);
				$('#stateMarketInfoList option[value="' + stateCode + '"]').attr('selected', 'selected');
			}
			loading.close();
		},
		error: function (xhr, textStatus, errorThrown) {
			$('#commodityMarketInfoList option[value="' + id + '"]').attr('selected', 'selected');
			$('#marketTable').empty();
			$('#marketTable').append('<tr><td colspan="12"><p class="my-2">' + xhr.responseJSON.error + '</p></td></tr>');
			$('#stateMarketInfoList').empty();
			$('#stateMarketInfoList').append(`<option selected="" disabled="">Select State</option>`);

			loading.close();
		},
	});
}

// State Wise Market Data Filter
function stateWiseMarketDataFilter(stateCode, commodityId) {
	// var loading = $.loading();

	var marketChartDataUrl = baseUrl + '/treading/view?stateCode=' + stateCode + '&districtCode=&commodityID=' + commodityId + '&pricingAgriVarietyID=&marketID=';
	$('#marketChartModal .modal-body .commodityName').empty();
	$.ajax({
		url: marketChartDataUrl,
		type: 'GET',
		dataType: 'json',
		type: 'GET',
		beforeSend: function (xhr) {
			// loading.open();
		},
		success: function (response) {
			// loading.close();
			$('#marketTable').empty();
			var trHTML = '';
			var stateDataArr = [];
			$.each(response, function (i, item) {
				trHTML += '<tr><td>' + item.arrivalDate + '</td><td class="text-left">' + item.stateName + '</td><td class="text-left">' + item.districtName + '</td><td class="text-left">' + item.marketName + '</td><td class="text-left">' + item.varietyName + '</td><td class="text-center charts"><img alt="" data-stateId="' + item.stateCode + '" data-districtId="' + item.districtCode + '" data-commodityId="' + item.commodityID + '" data-verietyId="' + item.pricingAgriVarietyID + '" data-marketId="' + item.marketID + '" src="images/icon/trend-graph.svg" width="25" onclick="marketChartData(event);" class="img-fluid allowed"></td><td>' + item.minPrice + '</td><td>' + item.maxPrice + '</td><td>' + item.modalPrice + '</td><td>' + item.minPriceChangePercentage + '</td><td>' + item.maxPriceChangePercentage + '</td><td>' + item.modalPriceChangePercentage + '</td></tr>';
			});
			$('#marketTable').append(trHTML);
		},
		error: function (xhr, textStatus, errorThrown) {
			loading.close();
			$('#marketTable').empty();
			$('#marketTable').append('<tr><td colspan="12"><p class="my-2">' + xhr.responseJSON.error + '</p></td></tr>');
		},
	});
}

// Gate value to display chart
var myChart;
// var chartDates = [];
// var chartModalData = [];
// var chartMinData;
// var chartMaxData;

// console.log(chartDates, chartModalData);
function marketChartData(event) {
	var loading = $.loading();
	var chartDates = [];
	// chartMinData = [];
	// chartMaxData = [];
	var chartModalData = [];
	var stateid = $(event.target).attr('data-stateid');
	var districtid = $(event.target).attr('data-districtid');
	var commodityid = $(event.target).attr('data-commodityid');
	var verietyid = $(event.target).attr('data-verietyid');
	var marketid = $(event.target).attr('data-marketid');
	var marketChartDataUrl = baseUrl + '/treading/view?stateCode=' + stateid + '&districtCode=' + districtid + '&commodityID=' + commodityid + '&pricingAgriVarietyID=' + verietyid + '&marketID=' + marketid;
	// console.log(marketChartDataUrl);
	var marketChartModal = document.getElementById('marketChartModal');
	if (marketChartModal) {
		$('#marketChartModal .modal-body .commodityName').empty();
		$.ajax({
			url: marketChartDataUrl,
			type: 'GET',
			dataType: 'json',
			beforeSend: function (xhr) {
				loading.open();
			},
			success: function (data) {
				loading.close();
				chartDates = data.date;
				// chartMinData = (data.min);
				// chartMaxData = (data.max);
				chartModalData = data.modal;
				$('#marketChartModal').modal('show');
				$('#marketChartModal .modal-body .commodityName').append(data.commodityName);

				createMarketChart(chartDates, chartModalData);
			},
			error: function (xhr, textStatus, errorThrown) {
				loading.close();
				setTimeout(function () {
					$('.msg-box').append(xhr.responseJSON.error).show();
					if ($('.msg-box:contains("' + xhr.responseJSON.error + '")').length > 0) {
						$('.msg-box').empty();
						$('.msg-box').append(xhr.responseJSON.error).show();
					}
				}, 0);

				setTimeout(function () {
					$('.msg-box').empty().hide();
				}, 3000);
			},
		});
	}
}

// Create Market Chart
function createMarketChart(chartDates, chartModalData) {
	if (myChart) {
		myChart.destroy();
	}

	// console.log(chartModalData);
	var minPrice = 0;
	var maxPrice = 0;

	maxPrice = Math.max.apply(Math, chartModalData); // 3
	minPrice = Math.min.apply(Math, chartModalData); //

	setValue = (maxPrice - minPrice) / 5;

	MinSetValue = minPrice - setValue;
	MaxSetValue = maxPrice + setValue;
	console.log(minPrice, maxPrice, setValue);

	// console.log("newMaxPrice " + MaxSetValue, "," + newMinPrice + "MinSetValue");

	var ctx = document.getElementById('marketChart').getContext('2d');
	myChart = new Chart(ctx, {
		type: 'line',
		data: {
			labels: chartDates,
			datasets: [
				// {
				//   label: 'Min Price',
				//   data: chartMinData,
				//   backgroundColor: 'rgba(41, 53, 232, 0.05)',
				//   borderColor: 'rgba(41, 53, 232, 0.99)',
				//   borderWidth: 1,
				//   // fill: false
				// },
				// {
				//   label: 'Max Price',
				//   data: chartMaxData,
				//   backgroundColor: 'rgba(0, 193, 163, 0.05)',
				//   borderColor: 'rgb(0, 193, 163, 0.99)',
				//   borderWidth: 1,
				//   // fill: false
				// },
				{
					label: 'Modal Price',
					data: chartModalData,
					backgroundColor: 'rgba(255, 37, 243, 0.05)',
					borderColor: 'rgba(255, 37, 243, 0.99)',
					borderWidth: 1,
					// fill: false
				},
			],
		},
		options: {
			responsive: true,
			responsiveAnimationDuration: 500,
			scales: {
				yAxes: [
					{
						display: true,
						// stacked: true,
						gridLines: {
							display: true,
							color: 'rgba(0, 0, 0, 0.03)',
						},
						// ticks: {
						//   beginAtZero: true,
						// },
						ticks: {
							beginAtZero: true,
							scaleOverride: true,
							stepSize: 5,
							maxTicksLimit: 5,
							// min: MinSetValue,
							// max: MaxSetValue
						},
						scaleLabel: {
							display: true,
							labelString: 'Price (Rs./Qtl)',
						},
					},
				],
				xAxes: [
					{
						gridLines: {
							display: true,
							color: 'rgba(0, 0, 0, 0.03)',
						},
						scaleLabel: {
							display: true,
							labelString: 'Dates',
						},
					},
				],
			},
		},
	});
}
