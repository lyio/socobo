module.exports = function (grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        bowercopy: {
            options: {
                srcPrefix: 'frontend/bower_components/',
                clean: 'true'

            },

            scripts: {
                options: {
                    destPrefix: ''
                },
                files: {
                    'public/javascripts': 'frontend/javascripts',
                    'public/images': 'frontend/images',
                    'public/stylesheets': 'frontend/stylesheets',
                    'public/base.html': 'frontend/index.html',
                    'public/elements': 'frontend/elements',

                    'public/bower_components': '*',
                    'public/bower_components/polymer/polymer.js': 'polymer/polymer.min.js',
                    'public/bower_components/platform/platform.js': 'platform/platform.js'
                }
            }
        },
        cdnify: {
            default: {
                options: {
                    rewriter: function (url) {
                        if (url.indexOf("google") === -1 && url.indexOf("..") != 0)
                            return "assets/" + url; // leave data URIs untouched
                        else
                            return url; // add query string to all other URLs
                    },
                    html: {
                        'img[src]': 'src',
                        'link[rel=stylesheet]': 'href',
                        'link[rel=import]': 'href',
                        'link[rel=manifest]': 'href',
                        'link[rel=icon]': 'href',
                        'link[rel="shortcut icon"]': 'href',
                        'script[src]': 'src',
                        'source[src]': 'src'
                    }
                },
                files: [{
                    expand: true,
                    cwd: 'public',
                    src: 'index.html',
                    dest: 'public'
                }]
            }
        }
    });

    // Load plugins
    grunt.loadNpmTasks('grunt-bowercopy');
    grunt.loadNpmTasks('grunt-cdnify');

    // Default task(s).
    grunt.registerTask('release', ['bowercopy', 'cdnify']);

};

