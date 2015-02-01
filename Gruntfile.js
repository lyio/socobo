module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        bowercopy: {
            options: {
                srcPrefix: 'frontend/bower_components/'

            },

            scripts: {
                options: {
                    destPrefix: ''
                },
                files: {
                    'app/assets/javascripts': 'frontend/javascripts',
                    'app/assets/images': 'frontend/images',
                    'app/assets/stylesheets': 'frontend/stylesheets',
                    'app/assets/base.html': 'frontend/index.html',

                    'frontend/javascripts': 'frontend/javascripts',
                    'frontend/images': 'frontend/images',
                    'frontend/stylesheets': 'frontend/stylesheets',
                    'app/assets/elements': 'frontend/elements',


                    'app/assets/bower_components' : '*'
                }
            }
        },
        vulcanize: {
            default: {
                options : {

                },
                files: {
                    'app/assets/index.html': 'app/assets/base.html'
                }
            }
        }
    });

    // Load the plugin that provides the "uglify" task.
    grunt.loadNpmTasks('grunt-bowercopy');
    grunt.loadNpmTasks('grunt-vulcanize');

    // Default task(s).
    grunt.registerTask('bower', ['bowercopy', 'vulcanize']);

};

