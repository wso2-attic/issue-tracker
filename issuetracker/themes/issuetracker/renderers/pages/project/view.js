var render = function (theme, data, meta, require) {
    theme('project', {
        title: [
            { partial:'title', context: data.title}
        ],
        nav: [
            { partial:'viewProjectNav', context: data.nav}
        ],
        body: [
            { partial:'viewProject', context: data.body}
        ]
    });
};

