$(document).ready(function() {
    // Gets the span width of the filled-ratings span
    // this will be the same for each rating
    var star_rating_width = $('.fill-ratings span').width();
    // Sets the container of the ratings to span width
    // thus the percentages in mobile will never be wrong
    $('.star-ratings').width(star_rating_width);
});