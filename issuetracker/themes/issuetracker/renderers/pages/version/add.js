var render = function (theme, data, meta, require) {
    theme('version', {
        title: [
			{ partial:'title', context: data.title}
		],
		nav: [
			{ partial:'versionNav', context: data.nav}
		],
		body: [
			{ partial:'addVersion', context: data.body}
		]
    });
};

