package view;

import java.util.Scanner;

import controller.ClassMemberController;
import dto.MemberDto;
import util.Constants;

import org.json.JSONObject;

public class View {
    private Scanner scanner;
    private MemberDto[] memberList;

    private ClassMemberController classMemberController;

    public View() {
        // init scanner
        this.scanner = new Scanner(System.in);
        this.memberList = null;
        this.classMemberController = new ClassMemberController();
    }
    
    public boolean display() {
        boolean isQuit = false;
        while(true) {
            displayMembers();
            displayMenu();
            System.out.println("Moi ban chon menu: ");
            int menu = scanner.nextInt();
            scanner.nextLine();
            switch (menu) {
                case 1:{ // refresh
                    getMemberList();
                    break;
                }
                case 2: { //add teacher
                    addNewTeacher();
                    break;
                }
                case 3: { //add student
                    addNewStudent();
                    break;
                }
                case 4: { // search by keyword
                    break;
                }
                case 5: { // view member
                    break;
                }
                case 6: { // remove member
                    break;
                }
                case 7: {
                    isQuit = true;
                    break;
                }
                default:
                    break;
            }
            if (isQuit == true) {
                break;
            }
        }
        if (isQuit == true) {
            this.quit();
        }
        return true;
    }

    /**
     * display list of members (include teacher and student)
     */
    private void displayMembers() {
        System.out.println("");
        System.out.println("=============================================================");
        System.out.println("Danh sach lop");
        System.out.printf("%-30s", "Ten");
        System.out.printf("%-10s", "Chuc vu");
        System.out.printf("%-10s", "Gioi Tinh");
        System.out.printf("%-30s", "Email");
        System.out.printf("%-12s", "SDT");
        System.out.println();
        if (memberList == null || memberList.length == 0) {
            System.out.println("Khong co thanh vien nao trong lop");
        } else {
            for (MemberDto member : this.memberList) {
                System.out.printf("%-30s", member.name);
                System.out.printf("%-10s", member.role);
                System.out.printf("%-10s", member.gender);
                System.out.printf("%-30s", member.email);
                System.out.printf("%-12s", member.phoneNumber);
                System.out.println("\n");
            }
        }
    }

    /**
     * display menu
     */
    private void displayMenu() {
        System.out.println("Menu:");
        System.out.println("[1] Refresh");
        System.out.println("[2] Add Teacher");
        System.out.println("[3] Add Student");
        System.out.println("[4] Search keyword");
        System.out.println("[5] View member");
        System.out.println("[6] Remove member");
        System.out.println("[7] Quit");
    }

    /**
     * input teacher info
     */
    private void addNewTeacher() {

        System.out.println("Nhap ten:");
        String name = this.scanner.nextLine();
        System.out.println("Nhap ngay sinh (dd/MM/yyy):");
        String birthday = this.scanner.nextLine();
        System.out.println("Nhap gioi tinh (Nam/Nu):");
        String gender = this.scanner.nextLine();
        System.out.println("Nhap email:");
        String email = this.scanner.nextLine();
        System.out.println("Nhap so dien thoai:");
        String phoneNumber = this.scanner.nextLine();
        System.out.println("Nhap so nam kinh nghiem:");
        String yearOfExperience = this.scanner.nextLine();
        System.out.println("Nhap linh vuc giang day:");
        String speciality = this.scanner.nextLine();

        // create teacher info params
        JSONObject teacherInfo = new JSONObject();
        teacherInfo.put("name", name);
        teacherInfo.put("birthday", birthday);
        teacherInfo.put("gender", gender);
        teacherInfo.put("email", email);
        teacherInfo.put("phoneNumber", phoneNumber);
        teacherInfo.put("yearOfExperience", yearOfExperience);
        teacherInfo.put("speciality", speciality);

        // System.out.println("" + teacherInfo);

        // call controller api
        JSONObject result = classMemberController.addTeacher(teacherInfo);
        if (result.getInt("status_code") == Constants.OK) {
            if (result.getBoolean("success") == true) {
                System.out.println("Tao moi teacher thanh cong ");
            } else {
                System.out.println("Tao moi teacher that bai ");
            }
        } else {
            System.out.println("Co loi o server");
        }

    }
    
    /**
     * input student info
     */
    private void addNewStudent() {
        System.out.println("Nhap ten:");
        String name = this.scanner.nextLine();
        System.out.println("Nhap ngay sinh (dd/MM/yyy):");
        String birthday = this.scanner.nextLine();
        System.out.println("Nhap gioi tinh (Nam/Nu):");
        String gender = this.scanner.nextLine();
        System.out.println("Nhap email:");
        String email = this.scanner.nextLine();
        System.out.println("Nhap so dien thoai:");
        String phoneNumber = this.scanner.nextLine();
        System.out.println("Dang ky hoc online (Y/N):");
        String isOnline = this.scanner.nextLine();
        System.out.println("Nhap background:");
        String background = this.scanner.nextLine();

        JSONObject studentInfo = new JSONObject();
        //put data to key
        studentInfo.put("name", name);
        studentInfo.put("birthday", birthday);
        studentInfo.put("gender", gender);
        studentInfo.put("email", email);
        studentInfo.put("phoneNumber", phoneNumber);
        studentInfo.put("isOnline", isOnline);
        studentInfo.put("background", background);
        // call api
        JSONObject result = classMemberController.addStudent(studentInfo);
        if (result.getInt("status_code") == Constants.OK) {
            if (result.getBoolean("success") == true) {
                System.out.println("Tao moi student thanh cong ");
            } else {
                System.out.println("Tao moi student that bai ");
            }
        } else {
            System.out.println("Co loi o server");
        }
    }

    private void getMemberList() {
        JSONObject result = classMemberController.getMemberList();
        JSONObject[] userListJson = (JSONObject[])result.get("data");
        this.memberList = new MemberDto[userListJson.length];
        int count = 0;
        for (JSONObject userJson : userListJson) {
            String name = userJson.get("name").toString();
            String role = userJson.get("role").toString();
            String gender = userJson.get("gender").toString();
            String email = userJson.get("email").toString();
            String phoneNumber = userJson.get("phoneNumber").toString();
            
            MemberDto member = new MemberDto();
            member.name = name;
            member.role = role;
            member.gender = gender;
            member.email = email;
            member.phoneNumber = phoneNumber;

            this.memberList[count++] = member;
        }


    }
    /**
     * system quit
     */
    private void quit() {
        System.out.println("Hen gap lai !!!");
        this.close();
    }

    // close view
    public void close() {
        if (this.scanner != null) {
            this.scanner.close();
        }
    }
}
