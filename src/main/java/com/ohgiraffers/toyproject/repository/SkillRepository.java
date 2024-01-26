package com.ohgiraffers.toyproject.repository;

import com.ohgiraffers.toyproject.aggregate.Skill;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SkillRepository {

    private List<Skill> skillList = new ArrayList<>();      // 불러오기용 List

    public SkillRepository() {

        // 저장용 List
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("몸통박치기", 20, "노멀"));
        skills.add(new Skill("100만볼트", 30, "전기"));
        skills.add(new Skill("불꽃세례", 30, "불"));
        skills.add(new Skill("물대포", 30, "물"));
        skills.add(new Skill("지진", 30, "땅"));
        skills.add(new Skill("잎날가르기", 30, "풀"));

        saveSkills(skills);
        loadSkills();
    }

    public void  saveSkills(List<Skill> skills) {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream("src/main/java/com/ohgiraffers/toyproject/db/skill.dat")));

            for(Skill s : skills) {
                oos.writeObject(s);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void loadSkills() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("src/main/java/com/ohgiraffers/toyproject/db/skill.dat")));

            while (true) {
                skillList.add((Skill) ois.readObject());
            }
        } catch (EOFException e) {
            System.out.println("스킬 정보 모두 로딩됨...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(ois != null) ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Skill> selectAllSkills() {
        return skillList;
    }

    public Skill selectSkill(String name) {
        for(Skill s : skillList) {
            if(s.getName().equals(name)) return s;
        }

        return null;
    }

}
