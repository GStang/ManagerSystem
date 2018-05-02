package com.swpuiot.managersystem.entity;

/**
 * Created by DELL on 2018/5/2.
 */
public class StudentAndClassInfo {
    /**
     * id : {"id":12345,"cNo":1}
     * attend : 出席
     */

    private AttendanceBean attendance;
    /**
     * attendance : {"id":{"id":12345,"cNo":1},"attend":"出席"}
     * invalidNumber : 377064
     */

    private String invalidNumber;

    public AttendanceBean getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceBean attendance) {
        this.attendance = attendance;
    }

    public String getInvalidNumber() {
        return invalidNumber;
    }

    public void setInvalidNumber(String invalidNumber) {
        this.invalidNumber = invalidNumber;
    }

    public static class AttendanceBean {
        /**
         * id : 12345
         * cNo : 1
         */

        private IdBean id;
        private String attend;

        public IdBean getId() {
            return id;
        }

        public void setId(IdBean id) {
            this.id = id;
        }

        public String getAttend() {
            return attend;
        }

        public void setAttend(String attend) {
            this.attend = attend;
        }

        public static class IdBean {
            private long id;
            private long cNo;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getCNo() {
                return cNo;
            }

            public void setCNo(long cNo) {
                this.cNo = cNo;
            }
        }
    }
}
