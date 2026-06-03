package ua.foxminded.university_schedule_app.Util;

public class PaginationValidator {
    
    private PaginationValidator() {
    }
    
    public static void validatePagination(int pageNumber, int pageSize) {
        if (pageNumber<0) {
            throw new IllegalArgumentException("pageNumber must be 0 and over");
        }
        if (pageSize>15 || pageSize<=0) {
            throw new IllegalArgumentException("pageSize must be from 1 to 15");
        }
    }
    
    
}
