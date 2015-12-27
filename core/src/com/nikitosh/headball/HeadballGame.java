package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.nikitosh.headball.screens.SplashScreen;

public class HeadballGame extends Game {
	@Override
	public void create () {
		setScreen(new SplashScreen(this));
	}
}/*

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.*;

public class HeadballGame extends ApplicationAdapter {
	World world;
	OrthographicCamera camera;
	Box2DDebugRenderer renderer;

	private RevoluteJoint rev;
	private Body visibleCircle, anchorPoint, rotator, box;

	@Override
	public void create() {
		camera = new OrthographicCamera(100, 100);
		camera.position.set(0, 0, 0);

		world = new World(new Vector2(0, 10), true);

		renderer = new Box2DDebugRenderer();

		visibleCircle = createCircle(5, 0, 0);
		visibleCircle.setType(BodyDef.BodyType.DynamicBody);

		anchorPoint = createBox(2, 2, 0, 0);
		anchorPoint.setType(BodyDef.BodyType.StaticBody);

		rotator = createBox(4, 4, 0, 0);
		rotator.setType(BodyDef.BodyType.DynamicBody);

		box = createBox(4, 4, 8, 0);
		box.setType(BodyDef.BodyType.DynamicBody);


		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.initialize(anchorPoint, rotator, anchorPoint.getWorldCenter());
		revoluteJointDef.enableMotor = true;
		revoluteJointDef.enableLimit = true;
		revoluteJointDef.motorSpeed = 2;
		revoluteJointDef.maxMotorTorque = 100000;
		revoluteJointDef.lowerAngle = -(float) Math.PI / 2;
		revoluteJointDef.upperAngle = 0;

		WeldJointDef weldJointDef = new WeldJointDef();
		weldJointDef.initialize(rotator, box, rotator.getWorldCenter());

		rev = (RevoluteJoint) world.createJoint(revoluteJointDef);
		world.createJoint(weldJointDef);

	}

	private Body createBox(float w, float h, float x, float y) {
		BodyDef nodeBodyDefinition = new BodyDef();
		nodeBodyDefinition.type = BodyDef.BodyType.DynamicBody;
		nodeBodyDefinition.position.set(10, 10);

		PolygonShape shape = new PolygonShape();
		float density = 1.0f;
		shape.setAsBox(w / 2.0f, h / 2.0f);

		Body body = world.createBody(nodeBodyDefinition);
		body.setUserData(this);
		body.setTransform(x, y, 0);
		FixtureDef nodeFixtureDefinition = createFixtureDefinition(shape, density);

		nodeFixtureDefinition.filter.categoryBits = 16;

		body.createFixture(nodeFixtureDefinition);
		shape.dispose();

		return body;
	}

	private Body createCircle(float r, float x, float y) {
		BodyDef nodeBodyDefinition = new BodyDef();
		nodeBodyDefinition.type = BodyDef.BodyType.DynamicBody;
		nodeBodyDefinition.position.set(10, 10);

		CircleShape shape = new CircleShape();
		float density = 1.0f;
		shape.setRadius(r);

		Body body = world.createBody(nodeBodyDefinition);
		body.setUserData(this);
		body.setTransform(x, y, 0);
		FixtureDef nodeFixtureDefinition = createFixtureDefinition(shape, density);
		nodeFixtureDefinition.filter.maskBits = 15;

		body.createFixture(nodeFixtureDefinition);
		shape.dispose();

		return body;
	}

	private static FixtureDef createFixtureDefinition(final Shape shape, final float density) {
		final FixtureDef nodeFixtureDefinition = new FixtureDef();
		nodeFixtureDefinition.shape = shape;
		nodeFixtureDefinition.friction = 1;
		nodeFixtureDefinition.density = density;
		nodeFixtureDefinition.restitution = 0.1f;
		return nodeFixtureDefinition;
	}


	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(Gdx.graphics.getDeltaTime(), 4, 4);
		camera.update();

		visibleCircle.setLinearVelocity(1f, 0f);
		anchorPoint.setTransform(visibleCircle.getPosition().x, visibleCircle.getPosition().y, anchorPoint.getAngle());
		renderer.render(world, camera.combined);

		Gdx.app.log("AAA", Float.toString(rev.getJointAngle()));
		Gdx.app.log("AAA", Float.toString(rev.getMotorSpeed()));
		if (Math.abs(rev.getJointAngle()) < 0.05) {
			rev.setMotorSpeed(-2);
		}
		if (Math.abs(rev.getJointAngle() + Math.PI / 2) < 0.05) {
			rev.setMotorSpeed(2);
		}
	}
}*/